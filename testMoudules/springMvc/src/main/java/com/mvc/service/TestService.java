package com.mvc.service;

import com.mvc.modules.Student;

import java.util.List;

public interface TestService {
    String testMethod1(String args);

    String testMethod2(String args);

    String testMethod3(String args);

    String testMethod4(String args);

    String testMethod5(String args);

    List<Student> testMethod6();

    void testMethod7();

    Object read();

    Object write();

    Object writeAndRead();

    Object testSource();

    Object testInclude();

    Object testKeyId();

    public void clearCache();
}
