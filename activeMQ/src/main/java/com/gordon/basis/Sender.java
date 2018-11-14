package com.gordon.basis;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 生产者
 * Created by gordon on 2018/9/8.
 */
public class Sender {

    public static final String userName = "";
    public static final String password = "";
//    public static final String userName = "admin";
//    public static final String password = "admin";
//    public static final String connect_url = "tcp://localhost:61616";// 测试本地broker方式
    public static final String connect_url = "tcp://192.168.157.4:61616";
    public static final String QUEUE = "queue";
    public static final String TOPIC = "topic";

    public static void main(String[] args) throws JMSException {
        testSender(QUEUE);
//        testSender(TOPIC);// 测试广播模式先启动客户端
    }

    private static void testSender(String mode) throws JMSException {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(userName,password,connect_url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        TextMessage massage = session.createTextMessage();
//        Queue queue = session.createQueue(QUEUE);
//        Topic topic = session.createTopic(TOPIC);
        Destination destination = null;
        if(mode.equals(QUEUE)){
            destination = session.createQueue(mode);
            massage.setText("测试queue队列模式");
        }else if (mode.equals(TOPIC)){
            destination = session.createTopic(mode);
            massage.setText("测试topic广播模式");
        }
        MessageProducer producer = session.createProducer(destination);
        // 使用MessageProducer的serDeliveryMode方法为其设置持久化特性和非持久化特性（DeliveryMode）
        // producer.setDeliveryMode(DeliveryMode.PERSISTENT);将消息传递特性置为持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);// 非持久化

        producer.send(massage);
        System.out.println("====生产者发送消息=====");
        if(connection != null){
            connection.close();
        }
        System.out.println("====生产者发送消息===结束==");
    }

}
