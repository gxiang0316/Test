package com.gordon.basis;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.jms.Destination;

/**
 * 消费者
 * Created by gordon on 2018/9/6.
 */
public class Receiver {

    public static void main(String[] args) throws JMSException {
        testReceiver(Sender.QUEUE);
//        testReceiver(Sender.TOPIC);
    }


    public static void testReceiver(String mode) throws JMSException {
        /* 1、建立ConnectionFactory工厂对象，需要填入用户名、密码、以及要连接的地址，
               注意不要包的路径，不要倒成spring里面的*/
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(Sender.userName,Sender.password,Sender.connect_url);

        /* 2、通过ConnectionFactory工厂对象创建一个Connection连接，
             并且调用Connection.start()方法开启连接，默认是关闭的*/
        Connection connection = connectionFactory.createConnection();
        connection.start();

        /* 3、通过Connection对象创建Session会话(上下文环境对象)，用于接收消息。Connection可以创建一个或多个Session
             注意：会话是单线程的，所以消息是连续的，就是说消息是按照发送的顺序一个一个接收的
             参数1：是否启用事务(表示不开启事务)   参数2：接收模式(一般设置为自动接收)*/
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        /* 4、通过Session创建Destination对象，即一个客户端用来指定生产者目标 和 消费者来源 的对象
                Destination destination = session.createQueue("queue");
              PTP模式中：Destination被称为Queue，队列
              Pub/Sub模式中：Destination被称为Topic，主题
              在程序中可以使用多个Queue 和 Topic */
        //Queue queue1 = session.createQueue(mode);// 注意要和消息生产者的队列名一致
        Destination destination = null;
        if(mode.equals(Sender.QUEUE)){
            destination = session.createQueue(mode);
        }else if (mode.equals(Sender.TOPIC)){
            destination = session.createTopic(mode);
        }


        /* 5、通过Session对象创建消息的发送对接收对象(生产者或消费者,MessageProducer/MessageConsumer)*/
        MessageConsumer messageConsumer = session.createConsumer(destination);
        // 多次运行main方法，测试创建多个消费者接收topic消息
        System.out.println("messageConsumer : " + messageConsumer.hashCode());
        /* 6、使用JMS规范消息类型创建消息数据
               1. StreamMessage : Java原始值的数据流
               2. MapMessage : 一套名称-值对
               3.TextMessage : 一个字符串对象
               4.ObjectMessage : 一个序列化的Java对象
               5.BytesMessage : 一个未解释字节的数据流
             发送端使用MessageProducer.send()方法发送数据
             接收端使用MessageConsumer.receive()接收数据 */
        //System.out.println("AAAAAAAAAA");
        //TextMessage msg = (TextMessage) messageConsumer.receive();
        // receiver()同步方式，执行后当前一直挂起，等待接收消息，后面那句打印B的语句没有输出，
        // 知道发送者发送消息，这里接收到消息才会继续往后执行
        //System.out.println("BBBBBBBBBB");

        //System.out.println("===消费者接收===" + msg.getText());
        /* 7、最后一定要关闭connection连接,否则ActiveMQ不能释放资源，
              关闭一个Connection同样也关闭了Session、MessageProducer、MessageConsumer。

              但是，客户端一般不要关闭连接，除非特殊情况，这也是手动关闭(在页面搞个按钮点击关闭)。
              为什么？？很简单，如果客户端关闭，那如何接收消息？难道每次都要手动开启连接？？这也太麻烦了点。
              当然也有这种操作，毕竟少。我们都希望它是自动接收，所以客户端一般都一直保持连接
              */
//        if(connection != null){
//            connection.close();
//            System.out.println("===消费者接收===结束===");
//        }


        // 生成环境一般是用监听器方式，此方式是异步执行，同时生产者开启事务或者使用持久化(持久化可以设置文件大小)
        messageConsumer.setMessageListener(new CustomMessageListener());



    }



}
