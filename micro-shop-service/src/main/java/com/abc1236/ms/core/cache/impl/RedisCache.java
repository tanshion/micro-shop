package com.abc1236.ms.core.cache.impl;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.cache.CacheDao;
import com.abc1236.ms.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;


@Slf4j
@Component
public class RedisCache implements CacheDao {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 将值 value 关联到 key
     */
    @Override
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
            stringRedisTemplate.opsForValue().set(key, cacheValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     */
    @Override
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
            stringRedisTemplate.opsForValue().set(key, cacheValue, duration);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取指定 key 的值
     */
    @Override
    public <V> V get(String key, TypeReference<V> type) {
        String value = stringRedisTemplate.opsForValue().get(key);
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
    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 将值 value 关联到 key存储在哈希表中
     */
    @Override
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
            stringRedisTemplate.opsForHash().put(key, hashKey, cacheValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 将值 value 关联到 key存储在哈希表中
     */
    @Override
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
            stringRedisTemplate.opsForHash().put(key, hashKey, cacheValue);
            stringRedisTemplate.expire(key, duration);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除
     */
    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(String... keys) {
        stringRedisTemplate.delete(Lists.newArrayList(keys));
    }

    /**
     * 获取存储在哈希表中指定字段的值
     */
    @Override
    public <V> V hGet(String key, String hashKey, TypeReference<V> type) {
        String value = (String) stringRedisTemplate.opsForHash().get(key, hashKey);
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
    @Override
    public String hGet(String key, String hashKey) {
        return (String) stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除一个或多个哈希表字段
     */
    @Override
    public Long hDelete(String key, Object... fields) {
        return stringRedisTemplate.opsForHash().delete(key, fields);
    }

}
