package com.mvc.utils;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class CustomCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(ConstantInfoUtil.AUTH_SYS_CACHE_PREFIX);
        String className = o.getClass().getSimpleName();

        sb.append(className + "-");
        for (Object obj : params) {
            sb.append(obj.toString());
        }
        return sb.toString();
    }
}
