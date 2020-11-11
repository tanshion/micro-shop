package com.abc1236.ms.cache.system;

import com.abc1236.ms.core.RedisHelper;
import com.abc1236.ms.entity.system.User;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Component
public class UserCache {

    private final RedisHelper redisHelper;

    private static final String key = "db:sys_user";
    public static final String byId = "id:{0}";

    public User byId(Long id) {
        return redisHelper.hGet(key, MessageFormat.format(byId,id) , new TypeReference<User>() {});
    }
}
