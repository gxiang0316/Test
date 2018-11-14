package com.gordon.utils;

import java.util.Set;

/**
 * 使用redis连接池方式
 * Created by gordon on 2018/7/31.
 */
public class JedisClientPool implements JedisClient {

    @Override
    public Boolean exists(String key) {
        return null;
    }

    @Override
    public Long del(String key) {
        return null;
    }

    @Override
    public String type(String key) {
        return null;
    }

    @Override
    public Set<String> keys(String pattern) {
        return null;
    }

    @Override
    public Long dbsize() {
        return null;
    }

    @Override
    public Long expire(String key, int timeout) {
        return null;
    }

    @Override
    public Long expireAt(String key, long millisecondsTimestamp) {
        return null;
    }

    @Override
    public Long ttl(String key) {
        return null;
    }

    @Override
    public String select(int index) {
        return null;
    }

    @Override
    public String flushdb() {
        return null;
    }

    @Override
    public String flushall() {
        return null;
    }

    @Override
    public String set(String key, String value) {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }
}
