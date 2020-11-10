package com.abc1236.ms.cache.impl;

import com.abc1236.ms.cache.CacheDao;
import com.abc1236.ms.core.RedisHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.abc1236.ms.constant.cache.Cache.CONSTANT;
import static com.abc1236.ms.constant.cache.Cache.timout;

/**
 * Ehcache缓存实现类<br>
 * 请不要直接使用该类，而是使用其接口CacheDao，以方便实际应用中往其他缓存服务切换（比如redis,ssdb等)
 *
 * @author enilu
 * @version 2018/9/12 0012
 */
@Component
@RequiredArgsConstructor
public class RedisCacheDao implements CacheDao {
    private final RedisHelper redisHelper;

    @Override
    public void hset(String key, String k, Object val) {
        redisHelper.hPut(key, k, val, timout(key));
    }

    @Override
    public <T> T hget(String key, String k, TypeReference<T> type) {
        return redisHelper.hGet(key, k, type);
    }


    @Override
    public String hget(String key, String k) {
        return redisHelper.hGet(key, k);
    }

    @Override
    public void set(String key, Object val) {
        redisHelper.hPut(CONSTANT, key, val, timout(CONSTANT));
    }

    @Override
    public <T> T get(String key, TypeReference<T> type) {
        return redisHelper.hGet(CONSTANT, key, type);
    }

    @Override
    public String get(String key) {
        return redisHelper.hGet(CONSTANT, key);
    }

    @Override
    public void del(String key) {
        redisHelper.hDelete(CONSTANT, key);
    }

    @Override
    public void hdel(String key, String k) {
        redisHelper.hDelete(CONSTANT, k);
    }
}
