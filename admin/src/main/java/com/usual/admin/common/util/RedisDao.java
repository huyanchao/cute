package com.usual.admin.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Repository
public class RedisDao {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     * @return 成功，失败
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @param timeUnit   时间类型
     * @return 成功，失败
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys 键
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern key的模式
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key 键
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key 键
     * @return 缓存中是否有对应的value
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object get(final String key) {
        ValueOperations operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 获取列表
     *
     * @param key 键
     * @return 列表值
     */
    public List<Map<String, Object>> getList(final String key) {
        List<Map<String, Object>> list = new ArrayList<>();

        Set<String> keys = redisTemplate.keys(key);
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            Map<String, Object> map = new HashMap<>();
            String keyName = it.next();
            Object value = this.get(keyName);
            map.put(keyName, value);
            list.add(map);
        }
        return list;
    }

    /**
     * 哈希 添加
     *
     * @param key     键值
     * @param hashKey 哈希键
     * @param value   哈希键对应的值
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key     键
     * @param hashKey 哈希键
     * @return 数据
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param key   键
     * @param value 值
     */
    public void lPush(String key, Object value) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(key, value);
    }

    /**
     * 列表获取
     *
     * @param key   键
     * @param start 索引的起点
     * @param end   索引的终点
     * @return 列表
     */
    public List<Object> lRange(String key, long start, long end) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(key, start, end);
    }

    /**
     * 集合添加
     *
     * @param key   键
     * @param value 值
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key 键
     * @return 集合
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * Add {@code value} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     *
     * @param key   键
     * @param value 值
     * @param score 排序的分值
     */
    public void zsetAdd(String key, Object value, double score) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, score);
    }

    /**
     * 获取最大值以及最小之间的有序集合
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 有序集合
     */
    public Set<Object> rangeByScore(String key, double min, double max) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.rangeByScore(key, min, max);
    }
}
