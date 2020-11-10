package com.abc1236.ms.cache;

import com.fasterxml.jackson.core.type.TypeReference;


/**
 * CacheDao
 *
 * @author enilu
 * @version 2018/9/12 0012
 */
public interface CacheDao {


    /**
     * 设置hash key值
     *
     * @param key
     * @param k
     * @param val
     */
    void hset(String key, String k, Object val);

    /**
     * 获取hash key值
     *
     * @param key
     * @param k
     * @return
     */
    String hget(String key, String k);

    /**
     * 获取hash key值
     *
     * @param key
     * @param k
     * @param typeReference
     * @param <T>
     * @return
     */
    <T> T hget(String key, String k, TypeReference<T> typeReference);

    /**
     * 设置key值，超时失效
     *
     * @param key
     * @param val
     */
    void set(String key, Object val);


    /**
     * 获取key值
     *
     * @param key
     * @param typeReference
     * @return
     */
    <T> T get(String key, TypeReference<T> typeReference);

    String get(String key);


    void del(String key);

    void hdel(String key, String k);
}
