package com.abc1236.ms.core.authentication;

import com.abc1236.ms.core.authentication.service.TokenService;
import com.abc1236.ms.core.authentication.token.AccessToken;
import com.abc1236.ms.core.authentication.token.AuthorizationUser;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.util.JsonUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * 认证成功处理器
 *
 * @author tanshion
 * @email 843565561@qq.com
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Resource
    private TokenService tokenService;
    @Resource
    private ClientDetailsService clientDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    //@Lazy
    @Resource
    private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        log.info("登录成功[{}]", authentication.getPrincipal());
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        String[] tokens = extractAndDecodeHeader(header, request);
        Assert.isTrue(tokens.length == 2, "请求头中client信息错误");
        String clientId = tokens[0];
        String clientSecret = tokens[1];
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }
        TokenRequest tokenRequest = new TokenRequest(Maps.newHashMap(), clientId, clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        AuthorizationUser authorizationUser = (AuthorizationUser) oAuth2Authentication.getPrincipal();
        OAuth2AccessToken oAuth2AccessToken = defaultAuthorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        AccessToken accessToken = buildAccessToken(clientDetails, authorizationUser, oAuth2AccessToken);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(JsonUtils.to(ResultEntity.success(accessToken)));
    }

    //GrantType包含stateless为无状态token，否则是有状态的
    private AccessToken buildAccessToken(ClientDetails clientDetails, AuthorizationUser authorizationUser, OAuth2AccessToken oAuth2AccessToken) {
        AccessToken accessToken;
        Set<String> grantTypes = clientDetails.getAuthorizedGrantTypes();
        if (grantTypes.contains("stateless")) {
            accessToken = new AccessToken();
            accessToken.setToken(oAuth2AccessToken.getValue());
        } else {
            String jti = (String) oAuth2AccessToken.getAdditionalInformation().get("jti");
            accessToken = tokenService.buildToken(clientDetails.getClientId(), "" + authorizationUser.getId(), jti);
            tokenService.saveToken(accessToken.getToken(), oAuth2AccessToken.getValue());
        }
        return accessToken;
    }


    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64Utils.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        String token = new String(decoded, StandardCharsets.UTF_8);
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

}
