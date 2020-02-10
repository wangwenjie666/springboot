package org.springboot.demo.pojo;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

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

    @CreationTimestamp
    @Column(updatable = false)
    private Date insertTime;
}
