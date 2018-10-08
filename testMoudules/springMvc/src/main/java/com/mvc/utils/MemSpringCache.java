package com.mvc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MemSpringCache implements Cache {

    private static Logger logger = LoggerFactory.getLogger(MemSpringCache.class);
    private String name;

    public static ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();

    //    public static Map<String, Object> map = new HashMap<String, Object>();
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.map;
    }

    @Override
    public ValueWrapper get(Object key) {
        String keyf = (String) key;
        Object object = null;
        logger.debug("key:" + keyf);
        object = map.get(keyf);
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
        String keyf = (String) key;
        map.put(keyf, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        String keyf = (String) key;
        map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    public void setName(String name) {
        this.name = name;
    }
}
