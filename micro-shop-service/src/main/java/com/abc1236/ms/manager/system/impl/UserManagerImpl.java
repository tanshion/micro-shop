package com.abc1236.ms.manager.system.impl;

import com.abc1236.ms.entity.system.User;
import com.abc1236.ms.manager.system.UserManager;
import com.abc1236.ms.mapper.system.UserMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserManagerImpl implements UserManager {
    private final UserMapper userMapper;
    //private final CacheDao cacheDao;

    @Override
    public User findByAccount(String username) {
        //User user = cacheDao.get(MessageFormat.format(DBCacheKey.sys_user_account, username),
        //    new TypeReference<User>() {});
        //if (user != null) {
        //    return user;
        //}
        //user = new QueryChain<>(userMapper)
        //    .eq(User::getAccount, username)
        //    .one();
        //cacheDao.set(MessageFormat.format(DBCacheKey.sys_user_account, username), user);
        //return user;
        return Db.lambdaQuery(User.class)
                .eq(User::getAccount, username)
                .one();

    }

    @Override
    public User findById(Long id) {
        //User user = cacheDao.get(MessageFormat.format(DBCacheKey.sys_user_id, id),
        //    new TypeReference<User>() {});
        //if (user != null) {
        //    return user;
        //}
        //user = new QueryChain<>(userMapper)
        //    .eq(User::getId, id)
        //    .one();
        //cacheDao.set(MessageFormat.format(DBCacheKey.sys_user_id, id), id);
        //return user;
        return Db.lambdaQuery(User.class)
                .eq(User::getId, id)
                .one();
    }

    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
        //deflectCache(user);
    }

    @Override
    public User findByPhone(String mobile) {
        return Db.lambdaQuery(User.class)
                .eq(User::getPhone, mobile)
                .one();
    }

    //private void deflectCache(User user) {
    //    cacheDao.delete(
    //        MessageFormat.format(DBCacheKey.sys_user_account, user.getAccount()),
    //        MessageFormat.format(DBCacheKey.sys_user_id, user.getId())
    //    );
    //}
}
