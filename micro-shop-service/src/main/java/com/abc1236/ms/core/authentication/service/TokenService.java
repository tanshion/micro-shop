package com.abc1236.ms.core.authentication.service;

import com.abc1236.ms.core.authentication.token.AccessToken;

public interface TokenService {
    void saveToken(String tokenKey, String tokenValue);

    void removeToken(String token);

    AccessToken buildToken(String clientId, String userId, String jti);
}
