package com.itx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/9 9:28
 */
@SpringBootApplication
@MapperScan("com.itx.crm.dao")
@EnableScheduling
public class SpringMyCRMStarter extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringMyCRMStarter.class);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(SpringMyCRMStarter.class);
//    }
}
