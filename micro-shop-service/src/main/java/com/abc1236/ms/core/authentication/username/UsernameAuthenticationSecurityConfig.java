package com.abc1236.ms.core.authentication.username;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信登录配置
 *
 * @author tanshion
 * @email 843565561@qq.com
 */
@Component
public class UsernameAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private UsernameAuthenticationProvider usernameAuthenticationProvider;

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.SecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.SecurityBuilder)
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        UsernameAuthenticationFilter usernameAuthenticationFilter = new UsernameAuthenticationFilter();
        usernameAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        usernameAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        usernameAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        http.authenticationProvider(usernameAuthenticationProvider)
            .addFilterAt(usernameAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
