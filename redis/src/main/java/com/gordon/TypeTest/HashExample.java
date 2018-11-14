package com.gordon.TypeTest;

import com.gordon.utils.JedisProvider;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by gordon on 2018/7/31.
 */
public class HashExample {

    public static void println(String content){
        System.out.println(content);
    }

    public static void main(String[] args) {
        Jedis jedis = JedisProvider.getJedis();

        String key = "goods";

        Long id = jedis.hset(key, "id", "1");
        println("hset结果 : " + id);
        String id1 = jedis.hget(key, "id");
        println("hget获取key为goods，field为id的值 : " + id1);
        Boolean id2 = jedis.hexists(key, "id");
        println("hexists : " + id2);
        Long hsetnx = jedis.hsetnx(key, "title", "商品001");
        println("hsetnx : " + hsetnx);

        println("======================================\r\n");
        // hmset 设置多个field
        Map<String ,String> msets = new HashMap<>();
        msets.put("color","red");
        msets.put("width","100");
        msets.put("height","80");
        String hmset = jedis.hmset(key, msets);
        println("hmset : " + hmset);
        List<String> hmget = jedis.hmget(key, new String[]{"color", "width", "height"});
        // 如果price存在值增加8，不存在则新建并赋值为8
        Long price = jedis.hincrBy(key, "price", 8);

        // 读取field数量
        Long hlen = jedis.hlen(key);
        println("======================================\r\n");
        // 读取key中所有field
        Set<String> hkeys = jedis.hkeys(key);
        for (Iterator<String> iterator = hkeys.iterator(); iterator.hasNext();){
            println("jedis.hkeys(key) : " + iterator.next());
        }
        println("======================================\r\n");
        // 读取key中所有的值
        List<String> hvals = jedis.hvals(key);
        for (int i = 0; i < hvals.size(); i++) {
            println("jedis.hvals(key) : " + hvals.get(i));
        }
        println("======================================\r\n");
        // 读取所有键值对
        Map<String, String> hgetAllMap = jedis.hgetAll(key);
        for (String field : hgetAllMap.keySet()){
            println("field : " + field + "  value : " + hgetAllMap.get(field));
        }
        println("======================================\r\n");
        // 删除一个field
        Long id3 = jedis.hdel(key, "id");
        // 删除多个field
//        Long hdel = jedis.hdel(key, "id", "color", "width");

        jedis.close();
    }

}
