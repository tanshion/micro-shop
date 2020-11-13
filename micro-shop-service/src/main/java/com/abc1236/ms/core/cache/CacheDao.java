package com.abc1236.ms.core.cache;

import com.fasterxml.jackson.core.type.TypeReference;

import java.time.Duration;


public interface CacheDao {

    /**
     * 将值 value 关联到 key
     */
    public <V> Boolean set(String key, V value);

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     */
    public <V> Boolean set(String key, V value, Duration duration);

    /**
     * 获取指定 key 的值
     */
    public <V> V get(String key, TypeReference<V> type);

    /**
     * 获取指定 key 的值
     */
    public String get(String key);

    /**
     * 将值 value 关联到 key存储在哈希表中
     */
    public <V> Boolean hPut(String key, String hashKey, V value);

    /**
     * 将值 value 关联到 key存储在哈希表中
     */
    public <V> Boolean hPut(String key, String hashKey, V value, Duration duration);

    /**
     * 删除
     */
    public void delete(String key);

    /**
     * 批量删除
     */
    public void delete(String... keys);

    /**
     * 获取存储在哈希表中指定字段的值
     */
    public <V> V hGet(String key, String hashKey, TypeReference<V> type);

    /**
     * 获取存储在哈希表中指定字段的值
     */
    public String hGet(String key, String hashKey);

    /**
     * 删除一个或多个哈希表字段
     */
    public Long hDelete(String key, Object... fields);

}
