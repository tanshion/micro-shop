package com.abc1236.ms.core.authentication.mobile;

import com.abc1236.ms.core.authentication.MyAuthenticationFailureHandler;
import com.abc1236.ms.core.authentication.MyAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 短信登录配置
 *
 * @author tanshion
 * @email 843565561@qq.com
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.SecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.SecurityBuilder)
     */
    @Override
    public void configure(HttpSecurity http) {

        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        http.authenticationProvider(smsCodeAuthenticationProvider)
            .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
