package com.mvc.controller;

import com.mvc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestMybatis {
    @Autowired
    TestService testService;

    @RequestMapping("test6")
    public Object test6() {
        return testService.testMethod6();
    }

    @RequestMapping("test7")
    public Object test7() {
        testService.testMethod7();
        return 1;
    }


    @RequestMapping("read")
    public Object read() {
        return testService.read();
    }

    @RequestMapping("write")
    public Object write() {
        return testService.write();
    }

    @RequestMapping("writeAndRead")
    public Object writeAndRead() {
        return testService.writeAndRead();
    }

    @RequestMapping("testSource")
    public Object testSource() {
        return testService.testSource();
    }

    @RequestMapping("testKeyId")
    public Object testKeyId() {
        return testService.testKeyId();
    }
}
