package com.abc1236.ms.core.authentication;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {
    public AuthException(String msg) {
        super(msg);
    }
}
