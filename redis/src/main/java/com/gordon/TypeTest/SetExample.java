package com.gordon.TypeTest;

import com.gordon.utils.JedisProvider;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Set类型特点：无序，不重复
 * Created by gordon on 2018/7/31.
 */
public class SetExample {

    public static void println(String content) {
        System.out.println(content);
    }

    public static void main(String[] args) {
        Jedis jedis = JedisProvider.getJedis();
        String setKey = "fruit2";
        // 向sets集合中添加元素，成功返回1，失败返回0，不能添加重复元素
        Long fruit = jedis.sadd(setKey, "apple");
        jedis.sadd(setKey,"banana","orange","grape");

        // 获取所有元素
        Set<String> smembers = jedis.smembers(setKey);
        for (String value : smembers){
            println("jedis.smembers 获取所有元素 : " + value);
        }
        println("======================================\r\n");

        // 删除元素
        Long apple = jedis.srem(setKey, "apple");

        // 判断元素是否存在
        Boolean apple1 = jedis.sismember(setKey, "apple");
        println("apple1是否存在 : " + apple1);

        // 统计元素个数
        Long scard = jedis.scard(setKey);
        println("元素总个数 : " + scard);

        // 随机获取一个元素
        String srandmember = jedis.srandmember(setKey);
        println("随机获取的元素 : " + srandmember);

        // 随机弹出一个元素
        String spop = jedis.spop(setKey);
        println("随机弹出一个元素 : " + spop);
        println("======================================\r\n");

        jedis.sadd("book","java","php","c++","android","ios","javascript");
        jedis.sadd("develop","c","ruby","python","java","android","ios","html");
        // 求交集
        Set<String> sinter = jedis.sinter("book", "develop");
        for (String value : sinter){
            println("交集 : " + value);
        }
        println("======================================\r\n");

        // 求交集并存储到指定的集合中
        Long sinterstore = jedis.sinterstore("newSet", "book", "develop");
        Set<String> newSet = jedis.smembers("newSet");
        for (String value : newSet){
            println("求交集后存到新集合newSet中 : " + value);
        }
        println("======================================\r\n");

        // 求并集
        Set<String> sunion = jedis.sunion("book", "develop");
        for (String value : sunion){
            println("求并集 : " + value);
        }
        println("======================================\r\n");

        // 求并集，并保存到新集合中
        Long sunionstore = jedis.sunionstore("newUnionSet", "book", "develop");

        // 求差集，注意这两个参数的顺序，如下得到的结果是：php  c++ javascript
        Set<String> sdiff = jedis.sdiff("book", "develop");
        for (String value : sdiff){
            println("求差集 : " + value);
        }
        println("======================================\r\n");
        Set<String> sdiff2 = jedis.sdiff("develop", "book");
        for (String value : sdiff2){
            println("求差集 : " + value);
        }
        //这个结果为：python html c ruby，可见谁在前面，结果就是从谁中取

        // 求差集，并保存到新的集合中
        Long sdiffstore = jedis.sdiffstore("newDiffSet", "book", "develop");


        jedis.close();
    }
}
