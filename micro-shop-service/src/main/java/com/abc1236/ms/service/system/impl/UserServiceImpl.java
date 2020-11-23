package com.abc1236.ms.service.system.impl;

import com.abc1236.ms.config.mybatis.wrapper.QueryChain;
import com.abc1236.ms.constant.cache.DBCacheKey;
import com.abc1236.ms.core.cache.CacheDao;
import com.abc1236.ms.entity.system.User;
import com.abc1236.ms.mapper.system.UserMapper;
import com.abc1236.ms.service.system.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final CacheDao cacheDao;

    @Override
    public User findByAccount(String username) {
        User user = cacheDao.get(MessageFormat.format(DBCacheKey.sys_user_account, username),
            new TypeReference<User>() {});
        if (user != null) {
            return user;
        }
        user = new QueryChain<>(userMapper)
            .eq(User::getAccount, username)
            .one();
        cacheDao.set(MessageFormat.format(DBCacheKey.sys_user_account, username), user);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = cacheDao.get(MessageFormat.format(DBCacheKey.sys_user_id, id),
            new TypeReference<User>() {});
        if (user != null) {
            return user;
        }
        user = new QueryChain<>(userMapper)
            .eq(User::getId, id)
            .one();
        cacheDao.set(MessageFormat.format(DBCacheKey.sys_user_id, id), id);
        return user;
    }

    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
        deflectCache(user);
    }

    @Override
    public User findByPhone(String mobile) {
        return new QueryChain<>(userMapper)
            .eq(User::getPhone, mobile)
            .one();
    }

    private void deflectCache(User user) {
        cacheDao.delete(
            MessageFormat.format(DBCacheKey.sys_user_account, user.getAccount()),
            MessageFormat.format(DBCacheKey.sys_user_id, user.getId())
        );
    }
}
