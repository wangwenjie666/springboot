package org.spring.demo.log.controller;

import org.spring.demo.log.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller
 *
 * @author wangwenjie
 * @date 2020-01-23
 */
@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/save")
    public String save() {
        userInfoService.save();
        return "ok";
    }
}
