package com.test.common.cacheImpl;

import com.test.common.util.RedisCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * auth: shi yi
 * create date: 2018/9/4
 */
@Component
public class MemSpringCache implements Cache {
    @Autowired
    private RedisCacheUtil redisCacheUtil;

    private static final String memCacheRedisPrefix = "mem_autho_temp_block_";
    private static final int block_time = 120;

    private static Logger logger = LoggerFactory.getLogger(MemSpringCache.class);
    private String name = "memCache";

    public static ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();

    //    public static Map<String, Object> map = new HashMap<String, Object>();

    public static Object updateRoleCache(String roleId) {
        return map.remove(roleId);
    }

    /**
     * 缓存的名字
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 得到底层使用的缓存
     */
    @Override
    public Object getNativeCache() {
        return this.map;
    }

    /**
     * 根据key得到一个ValueWrapper，然后调用其get方法获取值
     */
    @Override
    public ValueWrapper get(Object key) {
        String keyf = String.valueOf(key);
        Object object = null;
        logger.debug("memKey1:" + keyf);
        object = map.get(keyf);
        return (object != null ? new SimpleValueWrapper(object) : null);
    }

    /**
     * 根据key，和value的类型直接获取value
     */
    @Override
    public <T> T get(Object key, Class<T> type) {
        logger.debug("memKey2:" + key);
        Object object = this.map.get(String.valueOf(key));
        Object value = (object != null ? object : null);
        if (type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }


    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        String keyf = String.valueOf(key);
        logger.debug("memKey3:" + keyf);
        T object = null;
        object = (T) map.get(keyf);
        if (object != null || redisCacheUtil.exists(memCacheRedisPrefix + keyf)) {
            return object;
        }
        synchronized (this) {
            try {
                object = (T) map.get(keyf);
                if (object != null) {
                    return object;
                }
                object = valueLoader.call();
                if (object == null) {
                    redisCacheUtil.setObjectValue(memCacheRedisPrefix + keyf, object, block_time);
                } else {
                    put(key, object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (object != null ? object : null);
        }
    }

    /**
     * 存数据
     */
    @Override
    public void put(Object key, Object value) {
        String keyf = String.valueOf(key);
        map.put(keyf, value);
    }

    /**
     * 如果值不存在，则添加，用来替代如下代码
     * Object existingValue = cache.get(key);
     * if (existingValue == null) {
     * cache.put(key, value);
     * return null;
     * } else {
     * return existingValue;
     * }
     */
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    /**
     * 根据key删数据
     */
    @Override
    public void evict(Object key) {
        String keyf = String.valueOf(key);
        map.remove(key);
//        mqService.sendRoleOutDate(keyf);
    }

    /**
     * 清空数据
     */
    @Override
    public void clear() {
        map.clear();
//        mqService.sendRoleOutDate(ConstantInfoUtil.MEM_CACHE_CLEAR);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void delKeys(Collection<String> keys) {
        if (!CollectionUtils.isEmpty(keys)) {
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                evict(it.next());
            }
        }
    }

    /**
     * 删除mq推过来的内存缓存
     *
     * @param key
     */
    public void evictFromMq(String key) {
        map.remove(key);
    }

    /**
     * mq推送过来的，清空内存缓存
     */
    public void clearFromMq() {
        map.clear();
    }

}
