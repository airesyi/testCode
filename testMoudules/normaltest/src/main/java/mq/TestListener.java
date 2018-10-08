package mq;

import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TestListener implements MessageListener {

    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            String jsonStr = tm.getText();
            System.out.println(jsonStr);
        } catch (Exception e) {
        }

    }
}
