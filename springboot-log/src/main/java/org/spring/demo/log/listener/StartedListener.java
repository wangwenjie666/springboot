package org.spring.demo.log.listener;

import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动监听类
 *
 * @author wangwenjie
 * @date 2020-01-22
 */
@Slf4j
@Component
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

//    private static final Logger log = LoggerFactory.getLogger(StartedListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        ConfigurableEnvironment env = context.getEnvironment();
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String host = localHost.getHostAddress();
            String port = env.getProperty("server.port");
            log.info("application started at http://{}:{}", host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
