package org.spring.demo.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动核心类
 *
 * @author wangwenjie
 * @date 2020-01-22
 */
//@EnableJpaRepositories
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
