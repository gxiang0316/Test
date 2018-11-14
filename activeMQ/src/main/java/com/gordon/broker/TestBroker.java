package com.gordon.broker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URI;

/**
 * 一个broker相当于一个ActiveMQ服务器实例
 * ActiveMQ支持多个broker
 * Created by gordon on 2018/9/22.
 */
public class TestBroker {

    public static void main(String[] args) throws Exception {
        testBrokerService();
        //testBrokerFactory();

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public static void testBrokerService() throws Exception{
        BrokerService broker = new BrokerService();
        broker.setUseJmx(true);
        broker.addConnector("tcp://localhost:61616");
        broker.start();// broker启来后一直挂起
        //broker.stop();
    }

    public static void testBrokerFactory() throws Exception{
        String Uri = "properties:broker.properties";
        BrokerService broker1 = BrokerFactory.createBroker(new URI(Uri));
        broker1.addConnector("tcp://localhost:61616");
        broker1.start();
        /**
         broker.properties的内容示例如下：
         useJmx=true
         persistent=false
         brokerName=gordon
         */
    }


}
