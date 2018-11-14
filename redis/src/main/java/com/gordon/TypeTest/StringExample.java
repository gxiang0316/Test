package com.gordon.TypeTest;

import com.gordon.utils.JedisProvider;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 测试Redis String类型
 * Created by gordon on 2018/7/31.
 */
public class StringExample {

    public static void println(String content){
        System.out.println(content);
    }

    public static void main(String[] args) {
        Jedis jedis = JedisProvider.getJedis();
        // 清空数据库
        String s = jedis.flushDB();
        println("删除库中的key ： " + s);

        String key = "redis_test";
        //判断一个key是否存在
        Boolean exists = jedis.exists(key);
        println("exists : " + exists);
        if(exists){
            Long del = jedis.del(key);
            println("删除一个key ：" + del);
        }
        // 添加成功  返回OK
        String setResult = jedis.set(key, "a string value");
        jedis.set("redis_test1", "a string value 1");
        jedis.set("redis_test2", "a string value 2");
        println("添加一个key并赋值 ： " + setResult);
        Long strlen = jedis.strlen(key);
        println("获取key对应的字符串长度 ： " + strlen);
        String result = jedis.get(key);
        println("获取key对应的值 ： " + result);
        Long expire = jedis.expire(key, 20);
        println("设置key的过期时间，单位为秒 : " + expire);
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
        long currentTime = System.currentTimeMillis();
        String cTime = format.format(currentTime);
        println("cTime : " + cTime);
        currentTime = currentTime + (1000*20);// 增加20秒
        cTime = format.format(currentTime);
        println("cTime : " + cTime);
        // 设置key在当前时间延后20秒所在的时间过期，这样跟上面没什么区别，
        // 但是当我要精确控制在2018-08-23 12:00:00点过期呢，
        // expire()就要通过一系列计算了，而使用expireAt()方法更方便些
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018,8,23,12,0,0);
        long timeInMillis = calendar.getTimeInMillis();
        Long aLong = jedis.pexpireAt(key, currentTime);
        println("设置key在那个时间戳时过期 : " + aLong);

        Set<String> keys = jedis.keys("*");
//        Iterator<String> iterator = keys.iterator();
//        while (iterator.hasNext()){
//            println("key : " + iterator.next());
//        }

//        for (Iterator it1 = keys.iterator();it1.hasNext();){
//            println("key : " + it1.next());
//        }

        for (String value : keys){
            println("key : " + value);
        }

        String type = jedis.type(key);
        println("key type : " + type);
        Long aLong1 = jedis.dbSize();
        Long ttl = jedis.ttl(key);
        println("key的存活时间 : " + ttl);

        String select = jedis.select(2);
        println("按索引查询 : " + select);
        // 批量设置key-value
        jedis.mset("key1","value1","key2","value2","key3","value3");
        // 批量获取key对应的value
        List<String> mget = jedis.mget("key1", "key2", "key3");
        // 删除一个key
        Long key1 = jedis.del("key1");

        Long setnx = jedis.setnx("key4", "value4");
        println("如果key4不存在才添加 : " + setnx);
        String setex = jedis.setex("key5", 30, "15");
        println("添加key5并设置30秒后过期 ： " + setex);
        // 将key5的值加 1
        Long key5 = jedis.incr("key5");
        jedis.decr("key5");// 减一
        // 在后面追加值
        jedis.append("key4","4444");
        // 截取字符串
        jedis.substr("key4",0,6);

        jedis.close();
    }





}
