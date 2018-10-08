package com.test.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * auth: shi yi
 * create date: 2018/9/3
 */
@Component
public class ACMQListener {

    @JmsListener(destination = "springboottest")
    public void receiveMsg(String text) {
        System.out.println("get message:" + text);
    }
}
