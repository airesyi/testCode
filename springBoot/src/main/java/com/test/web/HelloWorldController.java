package com.test.web;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;


/**
 * auth: shi yi
 * create date: 2018/8/31
 */
@RestController
public class HelloWorldController {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("hello")
    public String index() {
        return "Hello World!!!";
    }

    @RequestMapping("produce")
    public void produce() {
        System.out.println("===========>>>>>>>>>> send message <<<<<<<<<========");
        Destination destination = new ActiveMQQueue("springboottest");
        jmsMessagingTemplate.convertAndSend(destination, "hello springboot");
    }

    @RequestMapping("testCache1")
    @Cacheable(value = "redisCache")
    public Object testCache1(String param) {
        System.out.println("no cache");
        return "helloCache:" + param;
    }

    @RequestMapping("testCache2")
    @Cacheable(value = "memCache")
    public Object testCache2(String param) {
        System.out.println("no cache");
        return "helloCache:" + param;
    }
}