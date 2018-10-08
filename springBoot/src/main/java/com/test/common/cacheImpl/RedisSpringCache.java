package com.test.common.cacheImpl;

import com.test.common.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * auth: shi yi
 * create date: 2018/9/4
 */
@Component
public class RedisSpringCache implements Cache {
    private int cache_timeout = 3 * 24 * 60 * 60;

    private String name = "redisCache";

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisCacheUtil;
    }

    @Override
    public ValueWrapper get(Object key) {
        String keyf = (String) key;
        Object object = null;
        object = redisCacheUtil.getObjectValue(keyf);
        return (object != null ? new SimpleValueWrapper(object) : null);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        String keyf = String.valueOf(key);
        T object = null;
        object = (T) redisCacheUtil.getObjectValue(keyf);
        if (object != null || redisCacheUtil.exists(keyf)) {
            return object;
        }
        synchronized (this) {
            try {
                object = (T) redisCacheUtil.getObjectValue(keyf);
                if (object != null || redisCacheUtil.exists(keyf)) {
                    return object;
                }
                object = valueLoader.call();
                put(key, object);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (object != null ? object : null);
        }
    }

    @Override
    public void put(Object key, Object value) {
        // TODO Auto-generated method stub
        String keyf = (String) key;
        redisCacheUtil.setObjectValue(keyf, value, cache_timeout);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        // TODO Auto-generated method stub
        String keyf = (String) key;
        redisCacheUtil.delStringValue(keyf);
    }

    @Override
    public void clear() {
        redisCacheUtil.batchDel("aaa");
    }

    public void setCache_timeout(int cache_timeout) {
        this.cache_timeout = cache_timeout * 24 * 60 * 60;
    }

    public void setName(String name) {
        this.name = name;
    }



}
