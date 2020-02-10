package org.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 *
 * @author wangwenjie
 * @date 2020-01-26
 */
@SpringBootApplication
//开启事务管理
@EnableTransactionManagement
public class JpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class);
    }
}
