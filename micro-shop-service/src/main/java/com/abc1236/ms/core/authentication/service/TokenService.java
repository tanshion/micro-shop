package com.abc1236.ms.core.authentication.service;

public interface TokenService {
    void saveToken(String tokenKey, String tokenValue);

    void removeToken(String token);
}
