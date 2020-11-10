package com.abc1236.ms.service.impl;

import com.abc1236.ms.cache.CacheDao;
import com.abc1236.ms.config.mybatis.wrapper.QueryChainWrapper;
import com.abc1236.ms.constant.cache.Cache;
import com.abc1236.ms.dao.mapper.UserMapper;
import com.abc1236.ms.entity.system.User;
import com.abc1236.ms.service.ManagerService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final UserMapper userMapper;
    private final CacheDao cacheDao;

    public User findByAccount(String account) {

        //由于：@Cacheable标注的方法，如果其所在的类实现了某一个接口，那么该方法也必须出现在接口里面，否则cache无效。
        //具体的原因是， Spring把实现类装载成为Bean的时候，会用代理包装一下，所以从Spring Bean的角度看，只有接口里面的方法是可见的，其它的都隐藏了，自然课看不到实现类里面的非接口方法，@Cacheable不起作用。
        //所以这里手动控制缓存
        User user = cacheDao.hget(Cache.SESSION, account, new TypeReference<User>() {});
        if (user != null) {
            return user;
        }
        user = new QueryChainWrapper<>(userMapper)
            .eq(User::getAccount, account)
            .one();
        cacheDao.hset(Cache.SESSION, account, user);
        return user;
    }

}
