package org.springboot.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springboot.demo.exception.ServiceException;
import org.springboot.demo.utils.AjaxResult;
import org.springboot.demo.vo.ExceptionVo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

/**
 * 测试全局异常处理
 *
 * @author wangwenjie
 * @date 2020-02-04
 */
@RestController
@Slf4j
public class ExceptionController {

    @RequestMapping("/null")
    public String nullEx() {
        String str = null;
        str.split(",");
        return "ok";
    }

    @RequestMapping("/runtime")
    public String runtime() {
        Assert.isTrue(1 == 2, "runtime 异常");
        return "ok";
    }

    @RequestMapping("/throwable")
    public String throwEx() throws IOException {
        File file = new File("");
        boolean newFile = file.createNewFile();
        return "ok";
    }

    @RequestMapping("/errorlog")
    public AjaxResult error() {
        AjaxResult result = new AjaxResult();
        ExceptionVo vo = new ExceptionVo();
        try {
            int i = 1 / 0;
            vo.setErrorMsg("aaaa");
            result.setData(vo);
        } catch (Exception e) {
            log.info("error log = {}", e.getMessage());
            vo.setErrorMsg(e.getMessage());
            result.setStatus(500);
            result.setStatusText("catch e : " + e.getMessage());
//            return result;
        }
        return result;
    }

    @RequestMapping("/serviceEx")
    public String serviceEx() {
       throw new ServiceException().setErrData("order001");
    }
}
