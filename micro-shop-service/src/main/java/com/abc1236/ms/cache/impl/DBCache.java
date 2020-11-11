package com.abc1236.ms.cache.impl;

import com.abc1236.ms.cache.CacheDao;
import com.abc1236.ms.entity.system.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DBCache {
    public final CacheDao cacheDao;


    private final static String key_db_sys_user = "db:sys_user";
    public final static String userById = "id:{0}";


    public void setUser(String hashKey,User user) {
        cacheDao.hset(key_db_sys_user,hashKey,user);
    }

    //public User getUserById(Long id) {
    //    return cacheDao.hget(userKey, MessageFormat.format(userById, id), new TypeReference<User>() {});
    //}
}
