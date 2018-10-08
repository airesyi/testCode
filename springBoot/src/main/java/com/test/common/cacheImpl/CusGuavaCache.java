package com.test.common.cacheImpl;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * auth: shi yi
 * create date: 2018/9/26
 */
@Configuration
public class CusGuavaCache {
    public static final String HOTEL_POSTION = "hotel_position";
    //cache key
    @Value("${cache.guavaCache.hotelPosition.maxSize}")
    private long hotelPositionMaxSize = 1L;
    @Value("${cache.guavaCache.hotelPosition.duration}")
    private long hotelPositionDuration = 3L;

    @Bean
    public GuavaCache guavaCacheImpl() {
        return new GuavaCache(HOTEL_POSTION, CacheBuilder.newBuilder()
                        .recordStats()
                        .maximumSize(hotelPositionMaxSize)
                        .expireAfterWrite(hotelPositionDuration, TimeUnit.DAYS).build());
    }


}
