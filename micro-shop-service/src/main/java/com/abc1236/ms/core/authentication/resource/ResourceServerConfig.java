package com.abc1236.ms.core.authentication.resource;

import com.abc1236.ms.core.authentication.MyAuthenticationEntryPoint;
import com.abc1236.ms.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.abc1236.ms.core.authentication.username.UsernameAuthenticationSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.Resource;


/**
 * @author tanshion
 * @email 843565561@qq.com
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//激活方法上的PreAuthorize注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Resource
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Resource
    private UsernameAuthenticationSecurityConfig usernameAuthenticationSecurityConfig;
    @Resource
    private MyAccessDeniedHandler handler;
    @Resource
    private MyBearerTokenExtractor myBearerTokenExtractor;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 异常处理
        resources.authenticationEntryPoint(myAuthenticationEntryPoint).accessDeniedHandler(handler);
        resources.tokenExtractor(myBearerTokenExtractor);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(myAuthenticationEntryPoint)
            .accessDeniedHandler(handler)
            .and()
            .apply(usernameAuthenticationSecurityConfig)
            .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            .authorizeRequests().anyRequest()//.authenticated()
            .permitAll()
            .and()
            .csrf().disable();

    }


}
