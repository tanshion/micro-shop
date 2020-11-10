package com.abc1236.ms.core.authentication;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    String clientId;
    String clientSecret;
    String cookieDomain;
    String jwtSigningKey;
    int cookieMaxAge;
    int tokenValiditySeconds;
}
