package com.gordon;

import com.gordon.utils.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by gordon on 2018/7/31.
 */
public class SpringRedisTest {

    @Test
    public void jedisClientTest(){
        // 当使用集群时将单机版配置注释，使用单机是将集群配置注释
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("gordon_jedisClient","jedisClient");
        String result = jedisClient.get("gordon_jedisClient");
        System.out.println(result);
    }


}
