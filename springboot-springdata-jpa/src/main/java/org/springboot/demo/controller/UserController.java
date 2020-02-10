package org.springboot.demo.controller;

import org.springboot.demo.pojo.Boss;
import org.springboot.demo.pojo.UserInfo;
import org.springboot.demo.service.UserService;
import org.springboot.demo.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-01-30
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/save")
    public String save(){
        userService.save();
        return "ok";
    }

    @GetMapping("/query")
    public AjaxResult queryBoss(){
        AjaxResult ajaxResult = new AjaxResult();
        Boss boss = userService.queryBoss();
        ajaxResult.setData(boss);
        return ajaxResult;
    }

    @GetMapping("/query2")
    public UserInfo getUserinfo(){
        UserInfo userInfo = userService.getUserInfo();
        return userInfo;
    }

}
