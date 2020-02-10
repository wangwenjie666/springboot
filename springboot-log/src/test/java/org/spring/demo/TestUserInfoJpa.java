package org.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.demo.log.Application;
import org.spring.demo.log.dao.UserInfoDao;
import org.spring.demo.log.pojo.UserInfo;
import org.spring.demo.log.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试jpa
 *
 * @author wangwenjie
 * @date 2020-01-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class TestUserInfoJpa {
    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void insert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("张麻子");
        userInfo.setAge(50);
        userInfoDao.save(userInfo);
    }

    @Test
    public void testFindAllByName() {
        List<UserInfo> list = userInfoService.findAllByName("张麻子");
        log.info("list = {}", list);
    }

    @Test
    public void testEqual(){
        Specification equal = userInfoService.equal("name", "张麻子", "马冬梅");
        List<UserInfo> list = userInfoDao.findAll(equal);
        log.info("list = {},size = {}", list, list.size());

    }


}
