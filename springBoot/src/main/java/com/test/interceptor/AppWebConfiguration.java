package com.test.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * auth: shi yi
 * create date: 2018/9/27
 */
// spring boot 2.x
@Configuration
public class AppWebConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，连接以/admin为前缀的 url路径
        registry.addInterceptor(new AdminLoginInterceptor()).addPathPatterns("/admin/**");
    }
}
