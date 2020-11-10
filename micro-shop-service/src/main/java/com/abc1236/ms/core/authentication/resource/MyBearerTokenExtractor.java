package com.abc1236.ms.core.authentication.resource;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.bo.JwtUser;
import com.abc1236.ms.cache.CacheDao;
import com.abc1236.ms.constant.cache.Cache;
import com.abc1236.ms.core.authentication.constant.TokenConstant;
import com.abc1236.ms.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyBearerTokenExtractor extends BearerTokenExtractor {

    private final CacheDao cacheDao;
    private final TokenStore jwtTokenStore;

    @Override
    public Authentication extract(HttpServletRequest request) {
        String tokenValue = request.getHeader(TokenConstant.HEADER_NAME_TOKEN);
        String token = initToken(tokenValue);
        if (token != null) {
            try {
                OAuth2AccessToken accessToken = jwtTokenStore.readAccessToken(token);
                if (accessToken.isExpired()) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
            initJwtUser(token, request);
            return new PreAuthenticatedAuthenticationToken(token, "");
        }
        return null;
    }

    private String initToken(String tokenValue) {
        if (StrUtil.contains(tokenValue, TokenConstant.STATELESS_ACCESS_TOKEN)) {
            return cacheDao.hget(Cache.SESSION, tokenValue);
        }
        return tokenValue;
    }

    public static void initJwtUser(String token, HttpServletRequest request) {
        //解析jwt
        Jwt decode = JwtHelper.decode(token);
        //得到 jwt中的用户信息
        String claims = decode.getClaims();
        JwtUser jwtUser = JsonUtils.from(claims, new TypeReference<JwtUser>() {});
        request.setAttribute("jwtUser", jwtUser);
    }
}
