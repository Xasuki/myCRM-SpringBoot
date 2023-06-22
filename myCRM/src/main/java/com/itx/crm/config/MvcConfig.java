package com.itx.crm.config;

import com.itx.crm.exceptions.NoLoginException;
import com.itx.crm.interceptors.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author suki
 * @version 1.0
 * @date 2023/6/10 17:18
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public NoLoginInterceptor noLoginInterceptor(){
        return new NoLoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(noLoginInterceptor())
               .addPathPatterns("/**")
               .excludePathPatterns("/index","/user/login","/css/**","/images/**","/js/**","/lib/**");
    }
}
