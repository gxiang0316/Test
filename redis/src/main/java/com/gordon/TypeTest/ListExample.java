package com.gordon.TypeTest;

import com.gordon.utils.JedisProvider;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import java.util.List;

/**
 * Created by gordon on 2018/7/31.
 */
public class ListExample {


    public static void println(String content) {
        System.out.println(content);
    }

    public static void main(String[] args) {
        Jedis jedis = JedisProvider.getJedis();

        // lpush 在左边添加，第一个添加的出现在最后，
        // 所以下面添加后list中的顺序是banana,lemon,peach,orange,apple
        String leftKey = "fruit";
        jedis.lpush(leftKey,"apple");
        jedis.lpush(leftKey,"orange","peach","lemon","banana");

        Long llen = jedis.llen(leftKey);
        println("leftkey 所在list的长度 : " + llen);
        // 修改list中小标为2 位置上的值为orange2
        String orange2 = jedis.lset(leftKey, 2, "peach2");

        // 获取所有值, 0~-1：表示所有值，0~3表示取出前三个元素，如果想取最后三个元素：-4~-1
        List<String> lrange = jedis.lrange(leftKey, 0, -1);
        println("修改2位置后的值 : " + lrange.get(2));
        println("======================================\r\n");

        // rpush 在右边添加,第一个添加的在第一个位置
        String rightKey = "numbers";
        jedis.rpush(rightKey,"5");
        jedis.rpush(rightKey,"4","3","2","1");
        List<String> lrange1 = jedis.lrange(rightKey, 0, -1);
        println("rightkey 原始大小 size : " + lrange1.size());
        // 截取list中的元素,保留 1 到 3之间的元素
        String ltrim = jedis.ltrim(rightKey, 1, 3);
        println("jedis.ltrim 截取是否成功 : " + ltrim);
        List<String> lrange2 = jedis.lrange(rightKey, 0, -1);
        println("jedis.ltrim 截取之后的大小 size : " + lrange2.size());
        for (int i = 0; i < lrange2.size(); i++) {
            println("jedis.ltrim 截取之后所留元素  : " + lrange2.get(i));
        }

        println("======================================\r\n");

        // 排序
        SortingParams sortingParams = new SortingParams();
        sortingParams.alpha();// 增加字母排序，如果list中的值是数字，不需要这个方法
        sortingParams.limit(0,3);// 限制返回元素个数
        List<String> sort = jedis.sort(leftKey, sortingParams);
        for (int i = 0; i < sort.size(); i++) {
            println("排序后元素 : " + sort.get(i));
        }
        // 复杂排序参考：http://coyee.com/article/10691-redis-sort-with-jedis
        println("======================================\r\n");

        // 在指定元素前/后插入
        jedis.linsert(rightKey, BinaryClient.LIST_POSITION.BEFORE,"2","12");
        List<String> lrange3 = jedis.lrange(rightKey, 0, -1);
        println("jedis.ltrim 在指定元素前/后插入之后的大小 size : " + lrange3.size());
        for (int i = 0; i < lrange3.size(); i++) {
            println("jedis.ltrim 在指定元素前/后插入之后所有元素  : " + lrange3.get(i));
        }

        println("======================================\r\n");

        // 删除leftkey所在list中最后一个元素，并把这个元素添加到rightkey所在list的第一个的位置
        String rpoplpush = jedis.rpoplpush(leftKey, rightKey);
        println("rpoplpush : " + rpoplpush);// 返回的就是被删除的元素值
        List<String> leftList = jedis.lrange(leftKey, 0, -1);
        List<String> rightList = jedis.lrange(rightKey, 0, -1);
        for (int i = 0; i < leftList.size(); i++) {
            println("leftList : " + leftList.get(i));
        }
        println("===========================");
        for (int i = 0; i < rightList.size(); i++) {
            println("rightList : " + rightList.get(i));
        }
        println("======================================\r\n");

        // 删除最后一个元素，并返回这个元素值
        String rpop = jedis.rpop(leftKey);
        println("rpop : " + rpop);
        List<String> leftList2 = jedis.lrange(leftKey, 0, -1);
        for (int i = 0; i < leftList2.size(); i++) {
            println("rpop 之后的 leftList : " + leftList2.get(i));
        }
        jedis.close();
    }
}
