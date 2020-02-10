package org.spring.demo.log.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户信息
 *
 * @author wangwenjie
 * @date 2020-01-23
 */
@Entity
@Table(name = "userinfo")
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;
}
