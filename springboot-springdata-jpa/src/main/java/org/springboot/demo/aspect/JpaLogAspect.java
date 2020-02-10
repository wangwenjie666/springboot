package org.springboot.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springboot.demo.utils.QueryRule;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * jpa日志切面
 *
 * @author wangwenjie
 * @date 2020-01-29
 */
@Aspect
@Component
@Slf4j
public class JpaLogAspect {

    /**
     * 定义切入点
     * 1）execution(public * *(..))——表示匹配所有public方法
     * 2）execution(* set*(..))——表示所有以“set”开头的方法
     * 3）execution(* com.xyz.service.AccountService.*(..))——表示匹配所有AccountService接口的方法
     * 4）execution(* com.xyz.service.*.*(..))——表示匹配service包下所有的方法
     * 5）execution(* com.xyz.service..*.*(..))——表示匹配service包和它的子包下的方法
     */
    @Pointcut("execution(public * org.springboot.demo.dao.BaseRepository.*(..)))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        Object[] args = joinPoint.getArgs();
        printJpaQueryLog(args);
        return result;
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, InvalidDataAccessApiUsageException ex) {
        Object[] errorArgs = joinPoint.getArgs();
        printJpaQueryLog(errorArgs);
    }

    //jpa查询参数打印 分页参数，排序参数
    private void printJpaQueryLog(Object[] args) {
        if (args.length > 0 && args[0] instanceof QueryRule) {
            QueryRule queryRule = (QueryRule) args[0];
            Map paramtersMap = queryRule.getParamtersMap();
            log.info("==> Query Parameters = {}", paramtersMap);
        }
        if (args.length == 2 && args[1] instanceof Pageable) {
            Pageable pageable = (Pageable) args[1];
            int pageNo = pageable.getPageNumber();
            int size = pageable.getPageSize();
            if (pageNo == 0) {
                log.info("==> Page Parameters = { limit {} }", size);
            } else {
                log.info("==> Page Parameters = { limit {},{} }", pageNo * size, size);
            }
            Sort sort = pageable.getSort();
            log.info("==> Sort Parameters = {{}}", sort);
        }
        if (args.length == 3 && args[1] instanceof Integer && args[2] instanceof Integer) {
            QueryRule queryRule = (QueryRule) args[0];
            int pageNo = (int) args[1];
            int size = (int) args[2];
            if (pageNo == 0) {
                log.info("==> Page Parameters = { limit {} }", size);
            } else {
                log.info("==> Page Parameters = { limit {},{} }", pageNo * size, size);
            }
            Sort sort = queryRule.getSort();
            if (sort != null) {
                log.info("==> Sort Parameters = {{}}", sort);
            }
        }
    }
}
