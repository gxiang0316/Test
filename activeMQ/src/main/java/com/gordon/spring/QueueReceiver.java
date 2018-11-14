package com.gordon.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by gordon on 2018/9/22.
 */
public class QueueReceiver {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        String msg = (String) jmsTemplate.receiveAndConvert();
        System.out.println("activemq spring test 接收消息 : " + msg);
    }

}
