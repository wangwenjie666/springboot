package org.springboot.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springboot.demo.utils.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 全局异常处理
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({Throwable.class})
    public AjaxResult exceptionHandler(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        String uuid = UUID.randomUUID().toString();
        System.out.println("ex-uuid:[" + uuid + "]");
        ex.printStackTrace();
        log.info("ex-uuid:[" + uuid + "]", ex);
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setStatus(-1);
        ajaxResult.setStatusText(ex.getMessage());
        response.addIntHeader("exflag", 0);
        String err;
        try {
            err = URLEncoder.encode(ex.getLocalizedMessage(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            err = "An internal server error has occurred on the server. The requested operation failed.";
        }
        response.addHeader("excontent", "uuid:[" + uuid + "]" + err);
        return ajaxResult;
    }

    /*@ExceptionHandler(RuntimeException.class)
    public AjaxResult runtimeExceptionHandler(RuntimeException e) {
        //打印异常堆栈
        e.printStackTrace();
        log.info(e.getMessage());
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setStatus(-2);
        ajaxResult.setStatusText(e.getMessage());
        return ajaxResult;
    }

    @ExceptionHandler(NullPointerException.class)
    public AjaxResult nullPointerExceptionHandler(NullPointerException e) {
        String localizedMessage = ((Exception) e).getLocalizedMessage();
        log.info("local message = {}", localizedMessage);
        e.printStackTrace();
        log.info("error log = {}", e.getMessage());
        log.info("class = {}", e.getClass().getName());
        String name = e.getClass().getName();
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setStatus(-3);
        ajaxResult.setStatusText("空指针异常：" + e.getMessage());
        return ajaxResult;
    }*/

    @ExceptionHandler(Exception.class)
    public AjaxResult exceptionHandler(Exception e) {
        log.info("==> Global Exception Catch ...");
        log.info("error class = {} , error log = {}", e.getClass().getName(), e.getMessage());
        //打印异常堆栈
        e.printStackTrace();
        log.info(e.getMessage());
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setStatus(110);
        ajaxResult.setStatusText("fail: " + e.getMessage());
        return ajaxResult;
    }

    @ExceptionHandler(ServiceException.class)
    public String serviceExceptionHandler(ServiceException ex) {
        log.info("==> Service Exception Catch it... ");
        log.info("error log = {}", ex.getMessage());
        log.info("erro data = {}", ex.getErrData());
        return "error";
    }

}
