package com.gordon.utils;

import java.util.Set;

/**
 * 使用jedis操作redis
 * Created by gordon on 2018/7/30.
 */
public interface JedisClient {

    // 通用命令
    /**判断一个key是否存在，存在返回true*/
    Boolean exists(String key);
    /**删除一个key，成功返回 1 */
    Long del(String key);
    /**查询某个key所属类型*/
    String type(String key);
    /**查询所有匹配的key*/
    Set<String> keys(String pattern);
    /**获取当前数据库中key的数量*/
    Long dbsize();
    /**设置key的过期时间*/
    Long expire(String key,int timeout);
    /**设置key在哪个时间戳过期,注意与上面一个的区别
     * 上面一个是设置多少秒之后过期，这个是在哪个时间点过期
     * 如：2018-08-23 12:00:00，当到这个时间时key过期
     * 注意：要将时间转成毫秒数
     * Calendar calendar = Calendar.getInstance();
     calendar.set(2018,8,23,12,0,0);
     long timeInMillis = calendar.getTimeInMillis();
     Long aLong = jedis.pexpireAt(key, currentTime);
     */
    Long expireAt(String key ,long millisecondsTimestamp);
    /**获取key的存活时长*/
    Long ttl(String key);
    /**按index查询对应位置是否存在，成功返回OK*/
    String select(int index);
    /**删除当前选择数据库中的所有key*/
    String flushdb();
    /**删除所有数据库中的所有key*/
    String flushall();

    // String 类型
    String set(String key , String value);
    String get(String key);


    // List类型


    // Hash类型

    // Set类型

    // Sorted Set类型

    // Geo类型

}
