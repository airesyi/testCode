package com.mvc.service.impl;

import com.mvc.dao.SourceDAO;
import com.mvc.dao.TestDAO;
import com.mvc.modules.Source;
import com.mvc.modules.Student;
import com.mvc.service.TestService;
import com.mvc.service.TestService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestDAO testDAO;
    @Autowired
    SourceDAO sourceDAO;
    @Autowired
    TestService2 testService2;

    @Override
    @Cacheable(value = "queueCache")
    public String testMethod1(String args) {
        System.out.println("queueCache");
        return "queueCache";
    }

    @Override
    @Cacheable(value = "memCache")
    public String testMethod2(String args) {
        System.out.println("memCache");
        return "memCache";
    }

    @Async
    public String testMethod3(String args) {
        System.out.println("service:" + Thread.currentThread().getId());
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("service:" + Thread.currentThread().getId() + ". time:" + (end - start));
        return "test3";
    }

    public String testMethod4(String args) {
        System.out.println("service:" + Thread.currentThread().getId());
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("service:" + Thread.currentThread().getId() + ". time:" + (end - start));
        return "test4";
    }

    @Override
    @Cacheable(value = "queueCache", key = "T(com.mvc.utils.ConstantInfoUtil).AUTH_SYS_CACHE_PREFIX + '123'")
    public String testMethod5(String args) {
        System.out.println("queueCache");
        return "queueCache";
    }

    @Override
    public List<Student> testMethod6() {
        return testDAO.getStudent();
    }

    @Override
    public void testMethod7() {
        testDAO.testBigint();
    }

    @Override
    @Transactional
    public Object read() {
        return testDAO.read();
    }

    @Override
    public Object write() {
        return testDAO.write();
    }

    @Override
    @Transactional
    public Object writeAndRead() {
        int b = testDAO.read().size();
        int a = testDAO.write();

        return a + b;
    }

    @Override
    @CacheEvict(value = "queueCache")
    public Object testSource() {
        List<Source> sources = sourceDAO.testSource();
        return sources;
    }

    @Override
    @Cacheable(value = "queueCache")
    public Object testInclude() {
//        List<Project> projectList = testDAO.testInclude();
        System.out.println("testInclude");
        return 1;
    }

    @Override
    @Cacheable(value = "queueCache")
    public Object testKeyId() {
        Source source = new Source();
        source.setSourceId("999");
        source.setParentId("999");
        source.setSourceName("999");
//        testDAO.testKeyId(source);
        System.out.println("testKeyId");
        return source.getSourceName();
    }

    @CacheEvict(value = "queueCache", allEntries = true)

    public void clearCache() {

    }
}
