/*
package com.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.*;

@Controller
@RequestMapping("")
public class TestActive {
    @Autowired
    private JmsTemplate jmsQueueTemplate;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination topic4Create;
    @Autowired
    private Destination topic4Update;

    @RequestMapping(value = "/topic")
    @ResponseBody
    public void sendTopicMessage( final String msg){
        jmsTemplate.setDefaultDestination(topic4Create);
        Destination destination = jmsTemplate.getDefaultDestination();
        System.out.println(Thread.currentThread().getName()+" 向队列"+ destination +"发送消息---------------------->"+msg);
        jmsTemplate.send(destination,new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    @RequestMapping(value = "/topicA")
    @ResponseBody
    public void sendTopicMessageA( final String msg){
        jmsTemplate.setDefaultDestination(topic4Create);
        Destination destination = jmsTemplate.getDefaultDestination();

        System.out.println(Thread.currentThread().getName()+" 向队列"+ destination +"发送消息---------------------->"+msg);
        jmsTemplate.send(destination,new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    @RequestMapping(value = "/topicB")
    @ResponseBody
    public void sendTopicMessageB( final String msg) throws JMSException {
        jmsTemplate.setDefaultDestination(topic4Create);
        Destination destination = jmsTemplate.getDefaultDestination();
 */
/*       CachingConnectionFactory cachingConnectionFactory = (CachingConnectionFactory) jmsTemplate.getConnectionFactory();
        cachingConnectionFactory.setClientId();*//*


        System.out.println(Thread.currentThread().getName()+" 向队列"+ destination +"发送消息---------------------->"+msg);
        jmsTemplate.send(destination,new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    @RequestMapping(value = "/getTopic")
    @ResponseBody
    public void getTopicMessage(){
        jmsTemplate.setDefaultDestination(topic4Create);
        Destination destination = jmsTemplate.getDefaultDestination();
        System.out.println(Thread.currentThread().getName()+" 向队列"+ destination +"获取消息----------------------");
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        try {
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }



    @RequestMapping(value = "/sendMessage")
    @ResponseBody
    public Object sendMessage(final String msg) throws JMSException {
        Destination destination = jmsQueueTemplate.getDefaultDestination();
        System.out.println(Thread.currentThread().getName()+" 向队列"+destination+"发送消息---------------------->"+msg);
        jmsQueueTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
        return null;
    }
}
*/
