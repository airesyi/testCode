package com.test.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * auth: shi yi
 * create date: 2018/9/27
 */
public class AppWebConfiguration1_X extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginInterceptor()).addPathPatterns("/app/**");
        super.addInterceptors(registry);
    }
}
