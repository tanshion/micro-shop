package com.abc1236.ms.core.cache.impl;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.cache.CacheDao;
import com.abc1236.ms.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCache implements CacheDao {

    private final StringRedisTemplate redisTemplate;


    /**
     * 将值 value 关联到 key
     */
    public <V> Boolean set(String key, V value) {
        if (null == value) {
            return false;
        }
        String cacheValue;
        if (value instanceof String) {
            cacheValue = (String) value;
        } else {
            cacheValue = JsonUtils.to(value);
        }

        try {
            redisTemplate.opsForValue().set(key, cacheValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     */
    public <V> Boolean set(String key, V value, Duration duration) {
        if (null == value) {
            return false;
        }
        String cacheValue;
        if (value instanceof String) {
            cacheValue = (String) value;
        } else {
            cacheValue = JsonUtils.to(value);
        }

        try {
            redisTemplate.opsForValue().set(key, cacheValue, duration);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取指定 key 的值
     */
    public <V> V get(String key, TypeReference<V> type) {
        String value = redisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        V v = JsonUtils.from(value, type);
        if (null == v) {
            delete(key);
        }
        return v;
    }


    /**
     * 获取指定 key 的值
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 将值 value 关联到 key存储在哈希表中
     */
    public <V> Boolean hPut(String key, String hashKey, V value) {
        if (null == value) {
            return false;
        }
        String cacheValue;
        if (value instanceof String) {
            cacheValue = (String) value;
        } else {
            cacheValue = JsonUtils.to(value);
        }

        try {
            redisTemplate.opsForHash().put(key, hashKey, cacheValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 将值 value 关联到 key存储在哈希表中
     */
    public <V> Boolean hPut(String key, String hashKey, V value, Duration duration) {
        if (null == value) {
            return false;
        }
        String cacheValue;
        if (value instanceof String) {
            cacheValue = (String) value;
        } else {
            cacheValue = JsonUtils.to(value);
        }

        try {
            redisTemplate.opsForHash().put(key, hashKey, cacheValue);
            redisTemplate.expire(key, duration);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除
     */
    public void delete(String... keys) {
        redisTemplate.delete(Lists.newArrayList(keys));
    }

    /**
     * 获取存储在哈希表中指定字段的值
     */
    public <V> V hGet(String key, String hashKey, TypeReference<V> type) {
        String value = (String) redisTemplate.opsForHash().get(key, hashKey);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        V v = JsonUtils.from(value, type);
        if (null == v) {
            hDelete(key, hashKey);
        }
        return v;
    }

    /**
     * 获取存储在哈希表中指定字段的值
     */
    public String hGet(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除一个或多个哈希表字段
     */
    public Long hDelete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

}
