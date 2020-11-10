package com.abc1236.ms.core.authentication.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhailiang
 */
@Slf4j
@RequiredArgsConstructor
public class TokenJwtEnhancer implements TokenEnhancer {

    /* (non-Javadoc)
     * @see org.springframework.security.oauth2.provider.token.TokenEnhancer#enhance(org.springframework.security.oauth2.common.OAuth2AccessToken, org.springframework.security.oauth2.provider.OAuth2Authentication)
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new HashMap<>();
        Object principal = authentication.getPrincipal();
        AuthorizationUser userJwt = (AuthorizationUser) principal;
        info.put("id", userJwt.getId());
        info.put("account", userJwt.getAccount());
        info.put("name", userJwt.getName());
        info.put("deptId", userJwt.getDeptId());
        info.put("phone", userJwt.getPhone());
        info.put("roleList", userJwt.getRoleList());
        info.put("deptName", userJwt.getDeptName());
        info.put("roleNames", userJwt.getRoleNames());
        info.put("roleCodes", userJwt.getRoleCodes());
        info.put("urls", userJwt.getUrls());
        info.put("permissions", userJwt.getPermissions());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}

