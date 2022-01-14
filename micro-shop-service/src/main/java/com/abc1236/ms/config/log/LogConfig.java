package com.abc1236.ms.config.log;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {


    @Bean
    public LogRequestWrapperFilter logRequestWrapperFilter() {
        return new LogRequestWrapperFilter();
    }

    @Bean
    public FilterRegistrationBean<LogRequestWrapperFilter> filterRegistrationBean() {
        FilterRegistrationBean<LogRequestWrapperFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(logRequestWrapperFilter());
        //order的数值越小 则优先级越高
        filterRegistrationBean.setOrder(-1000);
        return filterRegistrationBean;
    }
}
