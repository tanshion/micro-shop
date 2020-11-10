package com.abc1236.ms.service.impl;

import com.abc1236.ms.cache.CacheDao;
import com.abc1236.ms.constant.cache.Cache;
import com.abc1236.ms.core.authentication.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final CacheDao cacheDao;

    @Override
    public void saveToken(String tokenKey, String tokenValue) {
        cacheDao.hset(Cache.SESSION, tokenKey, tokenValue);
    }

    @Override
    public void removeToken(String tokenKey) {
        cacheDao.hdel(Cache.SESSION, tokenKey);
    }
}
