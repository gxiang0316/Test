package com.gordon.basis;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息监听器，它会另起一个线程监听消息服务器
 * Created by gordon on 2018/9/8.
 */
public class CustomMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.println(" ===== 消费者 接收消息 === " + text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
