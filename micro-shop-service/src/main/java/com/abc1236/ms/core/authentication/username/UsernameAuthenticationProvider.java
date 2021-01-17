package com.abc1236.ms.core.authentication.username;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.authentication.AuthException;
import com.abc1236.ms.core.authentication.service.MyUserDetailsService;
import com.abc1236.ms.entity.system.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 短信登录验证逻辑
 * <p>
 * 由于短信验证码的验证在过滤器里已完成，这里直接读取用户信息即可。
 *
 * @author tanshion
 * @email 843565561@qq.com
 */
@Component
@Slf4j
public class UsernameAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private MyUserDetailsService myUserDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernameAuthenticationToken authToken = (UsernameAuthenticationToken) authentication;
        User sysUser = authenticationChecks(authToken);
        UserDetails user = myUserDetailsService.getUserDetails(sysUser);
        if (user == null) {
            throw new AuthException("无法获取用户信息");
        }
        UsernameAuthenticationToken authenticationResult = new UsernameAuthenticationToken(
            user, authentication.getCredentials(), user.getAuthorities());
        authenticationResult.setDetails(authToken.getDetails());
        return authenticationResult;
    }

    private User authenticationChecks(UsernameAuthenticationToken authToken) {
        String username = (String) authToken.getPrincipal();
        String password = (String) authToken.getCredentials();
        if (StrUtil.isBlank(username)) {
            log.info("用户名为空");
            throw new AuthException("用户名或密码错误");
        }
        if (StrUtil.isBlank(password)) {
            log.info("密码为空");
            throw new AuthException("用户名或密码错误");
        }
        User sysUser = myUserDetailsService.getSysUserByUsername(username);
        if (sysUser == null) {
            log.info("用户: {},查不到", username);
            throw new AuthException("用户名或密码错误");
        }
        String presentedPassword = authToken.getCredentials().toString();
        if (!passwordEncoder.matches(presentedPassword, sysUser.getPassword())) {
            log.info("密码错误");
            throw new AuthException("用户名或密码错误");
        }
        return sysUser;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernameAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
