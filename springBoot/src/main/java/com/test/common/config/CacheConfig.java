package com.test.common.config;

import com.test.common.cacheImpl.MemSpringCache;
import com.test.common.cacheImpl.RedisSpringCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * auth: shi yi
 * create date: 2018/9/4
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
    @Resource
    RedisSpringCache redisSpringCache;
    @Resource
    MemSpringCache memSpringCache;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

        Set<Cache> caches = new HashSet<>();
        caches.add(redisSpringCache);
        caches.add(memSpringCache);

        simpleCacheManager.setCaches(caches);

        return simpleCacheManager;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getSimpleName()).append("-");
                sb.append(method.getName()).append(":");
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

}
