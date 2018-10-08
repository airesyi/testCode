package mq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class MQUtil {
    public static void main(String[] args) {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(MQConnectFactory.getCachingConnectionFactory());

        ActiveMQTopic activeMQTopic = new ActiveMQTopic("testTopicAAAA");

        defaultMessageListenerContainer.setDestination(activeMQTopic);
        defaultMessageListenerContainer.setSubscriptionDurable(true);
        defaultMessageListenerContainer.setReceiveTimeout(10000);


        defaultMessageListenerContainer.setMessageListener(new TestListener());

        defaultMessageListenerContainer.start();
    }

}
