package com.abc1236.ms.core.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
            "/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
            "/swagger-resources/configuration/security", "/swagger-ui.html",
            "/webjars/**", "/course/coursepic/list/**",
            "/actuator/**"
        );
    }


    //@Override
    //public void configure(HttpSecurity http) throws Exception {
    //    http.formLogin()
    //        .and()
    //        .exceptionHandling()
    //        .authenticationEntryPoint(myAuthenticationEntryPoint)
    //        .accessDeniedHandler(handler)
    //        .and()
    //        .apply(usernameAuthenticationSecurityConfig)
    //        .and()
    //        .apply(smsCodeAuthenticationSecurityConfig)
    //        .and()
    //        .authorizeRequests().anyRequest().authenticated()
    //        .and()
    //        .csrf().disable();
    //}
}
