package com.gordon;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 原生代码操作redis
 * Created by gordon on 2018/7/31.
 */
public class RedisTest {

    @Test
    public void testJedis() throws Exception{
        // 创建Jedis连接对象，参数：host、port
        Jedis jedis = new Jedis("192.168.157.4",6379);
        // 直接使用jedis操作redis，所有jedis方法都对应redis命令
        jedis.set("test1","aaaa");
        String value = jedis.get("test1");
        System.out.println(value);
        // 每次使用完都要及时关闭
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception{
        // 创建一个连接池对象，两个参数host、port
        JedisPool jedisPool = new JedisPool("192.168.157.4",6379);
        // 从连接池中获得一个连接，就是一个jedis对象
        Jedis jedis = jedisPool.getResource();
        jedis.set("test2","bbbb");
        String result = jedis.get("test2");
        System.out.println(result);
        // 关闭连接
        jedis.close();
        // 关闭连接池
        jedisPool.close();
    }

    /**
     * redis集群测试
     * @throws Exception
     */
    @Test
    public void testJedisCluster() throws Exception {
        //创建一个JedisCluster对象。有一个参数nodes是一个set类型。set中包含若干个HostAndPort对象。
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.157.4", 7001));
        nodes.add(new HostAndPort("192.168.157.4", 7002));
        nodes.add(new HostAndPort("192.168.157.4", 7003));
        nodes.add(new HostAndPort("192.168.157.4", 7004));
        nodes.add(new HostAndPort("192.168.157.4", 7005));
        nodes.add(new HostAndPort("192.168.157.4", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //直接使用JedisCluster对象操作redis。
        jedisCluster.set("test3", "cccc");
        String string = jedisCluster.get("test3");
        System.out.println(string);
        //关闭JedisCluster对象
        jedisCluster.close();
    }

}
