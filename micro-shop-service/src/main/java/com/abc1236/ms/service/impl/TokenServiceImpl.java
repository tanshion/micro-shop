package com.abc1236.ms.service.impl;

import com.abc1236.ms.constant.cache.Cache;
import com.abc1236.ms.core.authentication.constant.TokenConstant;
import com.abc1236.ms.core.authentication.service.TokenService;
import com.abc1236.ms.core.authentication.token.AccessToken;
import com.abc1236.ms.core.cache.CacheDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final CacheDao cacheDao;
    Duration cacheTimeout = Duration.ofHours(2);

    @Override
    public void saveToken(String tokenKey, String tokenValue) {
        cacheDao.set(tokenKey, tokenValue, cacheTimeout);
    }

    @Override
    public void removeToken(String tokenKey) {
        cacheDao.delete(Cache.SESSION, tokenKey);
    }

    @Override
    public AccessToken buildToken(String clientId, String userId, String jti) {
        AccessToken accessToken = new AccessToken();
        String token = TokenConstant.STATELESS_ACCESS_TOKEN + ":" + clientId + ":" + userId + ":" + jti;
        accessToken.setToken(token);
        return accessToken;
    }
}
