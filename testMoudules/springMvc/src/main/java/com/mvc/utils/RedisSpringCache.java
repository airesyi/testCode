package com.mvc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;

public class RedisSpringCache implements Cache {

    private static Logger logger = LoggerFactory.getLogger(RedisSpringCache.class);

    private int cache_timeout = 3 * 24 * 60 * 60;

    private String name;

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
        logger.debug("key:" + keyf);
        object = redisCacheUtil.getObjectValue(keyf);
        return (object != null ? new SimpleValueWrapper(object) : null);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
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
        redisCacheUtil.batchDel(ConstantInfoUtil.AUTH_SYS_CACHE_PREFIX);
    }

    public void setCache_timeout(int cache_timeout) {
        this.cache_timeout = cache_timeout * 24 * 60 * 60;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RedisCacheUtil getRedisCacheUtil() {
        return redisCacheUtil;
    }

    public void setRedisCacheUtil(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }


}
