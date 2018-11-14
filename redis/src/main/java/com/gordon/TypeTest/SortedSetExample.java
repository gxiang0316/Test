package com.gordon.TypeTest;

import com.gordon.utils.JedisProvider;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * 有一个权重因子，sortedSet通过它来排序
 * Created by gordon on 2018/8/1.
 */
public class SortedSetExample {

    public static void println(String content) {
        System.out.println(content);
    }

    public static void main(String[] args) {
        Jedis jedis = JedisProvider.getJedis();

        String zsetKey = "fruit";
        // 添加元素
        Long apple = jedis.zadd(zsetKey, 5.0, "apple");
        jedis.zadd(zsetKey,1.0,"banana1");
        jedis.zadd(zsetKey,2.0,"banana2");
        jedis.zadd(zsetKey,3.0,"banana3");
        jedis.zadd(zsetKey,4.0,"banana4");
        jedis.zadd(zsetKey,6.0,"banana6");
        jedis.zadd(zsetKey,7.0,"banana7");

        // 统计元素个数
        Long zcard = jedis.zcard(zsetKey);
        println("统计元素个数 : " + zcard);

        // 统计某个权重范围内的元素个数
        Long zcount = jedis.zcount(zsetKey, 2.0, 5.0);
        println("统计某个权重范围内的元素个数 : " + zcount);

        // 获取某个元素的权重
        Double banana41 = jedis.zscore(zsetKey, "banana4");
        println("获取某个元素的权重 : " + banana41);

        // 查看某个元素在集合中排名，zrank：按score从小到大排序
        Long banana3 = jedis.zrank(zsetKey, "banana3");
        println("查看banana3在集合中排名 : " + banana3);

        // 查看某个元素在集合中排名，zrevrank：按score从大到小排序
        Long banana4 = jedis.zrevrank(zsetKey, "banana3");
        println("查看banana3在集合中排名 : " + banana4);

        // 查看某个区间排序后的元素，zrange:按score从小到大排序
        Set<String> zrange = jedis.zrange(zsetKey, 2, 5);
        for (String value : zrange){
            println("jedis.zrange 某个区间排序后的元素 : " + value);
        }
        println("======================================\r\n");

        // 查看某个区间排序后的元素，zrevrange:按score从大到小排序
        Set<String> zrange2 = jedis.zrevrange(zsetKey, 2, 5);
        for (String value : zrange2){
            println("jedis.zrevrange 某个区间排序后的元素 : " + value);
        }
        println("======================================\r\n");

        // 获取某个权重区间的元素，zrangebyscore
        Set<String> strings = jedis.zrangeByScore(zsetKey, 3.0, 6.0);
        for (String value : strings){
            println("zrangebyscore 获取某个权重区间的元素 : " + value);
        }

        // 按权重从小到大排序并返回范围内所有元素和元素的权重
        Set<Tuple> tuples = jedis.zrangeWithScores(zsetKey, 0, -1);
        for (Tuple tuple : tuples){
            println("按权重排序并返回范围内所有元素和元素的权重 : " + "  元素 = " + tuple.getElement() + "  权重 = " + tuple.getScore());
        }
        println("======================================\r\n");


        //按权重从大到小排序并返回范围内所有元素和元素的权重
        Set<Tuple> tuples2 = jedis.zrevrangeWithScores(zsetKey, 0, -1);
        for (Tuple tuple : tuples2){
            println("按权重排序并返回范围内所有元素和元素的权重 : " + "  元素 = " + tuple.getElement() + "  权重 = " + tuple.getScore());
        }
        println("======================================\r\n");

        //
        Set<Tuple> tuples3 = jedis.zrangeByScoreWithScores(zsetKey, 0, -1);
        for (Tuple tuple : tuples3){
            println("按权重排序并返回范围内所有元素和元素的权重 : " + "  元素 = " + tuple.getElement() + "  权重 = " + tuple.getScore());
        }
        println("======================================\r\n");

        Set<Tuple> tuples4 = jedis.zrevrangeByScoreWithScores(zsetKey, 0, -1);
        for (Tuple tuple : tuples4){
            println("按权重排序并返回范围内所有元素和元素的权重 : " + "  元素 = " + tuple.getElement() + "  权重 = " + tuple.getScore());
        }
        println("======================================\r\n");

        // 求交集,并保存到新的sortedset中
        //jedis.zinterstore()

        // 求并集,并保存到新的sortedset中
        //jedis.zunionstore()

        // sortedset 没有求差集的方法

        // 增加某个元素的权重
        Double banana31 = jedis.zincrby(zsetKey, 9.0, "banana3");
        println("增加后的权重 : " + jedis.zscore(zsetKey,"banana3"));

        // 删除某个元素
        Long banana1 = jedis.zrem(zsetKey, "banana1");

        // 删除某个区间的元素
        Long aLong = jedis.zremrangeByRank(zsetKey, 0, 2);

        // 按权重区间删除元素
        Long aLong1 = jedis.zremrangeByScore(zsetKey, 4.0, 5.0);



        jedis.close();
    }
}
