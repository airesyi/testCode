package com.mvc.service.impl;

import com.mvc.dao.TestDAO;
import com.mvc.service.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * auth by shiyi
 * updateTime ${DATA}
 */
@Service
public class TestService2Impl implements TestService2 {
    @Autowired
    TestDAO testDAO;

    @Override
    @Cacheable(value = "queueCache")
    public Object testService1Impl() {
        System.out.println("testService1Impl");
        return 1;
    }

    @Override
    @Cacheable(value = "mycache", key = "#param", cacheManager = "cacheManager1")
    public Object testService2Impl(String param) {
        System.out.println("no ehCache");
        return "ehCache:" + param;
    }
}
