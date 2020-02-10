package org.springboot.demo.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springboot.demo.dao.BossDao;
import org.springboot.demo.dao.UserInfoRepository;
import org.springboot.demo.exception.ServiceException;
import org.springboot.demo.pojo.Boss;
import org.springboot.demo.pojo.Emp;
import org.springboot.demo.pojo.UserInfo;
import org.springboot.demo.utils.QueryRule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * service
 *
 * @author wangwenjie
 * @date 2020-01-30
 */
@Transactional
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BossDao bossDao;


    public void save() {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setAge(1);
            userInfo.setName("马冬梅");
            userInfoRepository.save(userInfo);
        } catch (Exception e) {
            log.info("抛出运行时异常，交给spring处理...");
            throw new RuntimeException(e.getMessage());
        }
    }

    // 事务回滚测试
    public void saveRollback() {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setAge(20);
            userInfo.setName("夏洛roll-back");
            userInfoRepository.save(userInfo);
            int i = 1 / 0;
        } catch (Exception e) {
            log.info("捕获异常，抛出自定义异常");
            throw new ServiceException(e.getMessage());
        }
    }

    public Boss saveBoss() {
        Boss boss = new Boss();
        boss.setBossName("张麻子");
        Emp emp = new Emp();
        emp.setEmpName("汤师爷");
        emp.setBoss(boss);
        Emp emp1 = new Emp();
        emp1.setEmpName("夫人");
        emp1.setBoss(boss);
        ArrayList<Emp> list = Lists.newArrayList();
        list.add(emp);
        list.add(emp1);
        boss.setEmpList(list);
        Boss save = bossDao.save(boss);
        return save;
    }

    public Boss queryBoss() {
        Boss boss = bossDao.findById(3).get();
        return boss;
    }

    public UserInfo getUserInfo() {
        QueryRule<UserInfo> queryRule = QueryRule.getInstance();
        queryRule.addEqual("id", 3);
        Optional<UserInfo> one = userInfoRepository.findOne(queryRule);
        return one.get();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testJdbcTemplate() throws SQLException {
        DataSource dataSource = jdbcTemplate.getDataSource();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            String sql = "insert into userinfo(name,age) values('老汤',12)";
            jdbcTemplate.execute(sql);
            connection.commit();

            UserInfo userInfo = new UserInfo();
            userInfo.setAge(1);
            userInfo.setName("马冬梅");
            userInfoRepository.save(userInfo);

            log.info("新增完成");
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
