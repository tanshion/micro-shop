package com.abc1236.ms.core.authentication;

import com.abc1236.ms.core.authentication.token.TokenJwtEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@EnableConfigurationProperties(AuthProperties.class)
@Configuration
public class SecurityConfig {
    @Autowired
    AuthProperties authProperties;

    @Bean
    PasswordEncoder passwordEncoder() {
        //DelegatingPasswordEncoder delegatingPasswordEncoder =
        //    (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //设置defaultPasswordEncoderForMatches为NoOpPasswordEncoder
        //delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new MessageDigestPasswordEncoder("SHA-1"));
        //return  delegatingPasswordEncoder;
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return new TokenJwtEnhancer();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(authProperties.getJwtSigningKey());
        return converter;
    }


}
