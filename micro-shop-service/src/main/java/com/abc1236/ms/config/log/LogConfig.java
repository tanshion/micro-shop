package com.abc1236.ms.config.log;


import com.purgerteam.log.trace.starter.filter.TraceFilter;
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
        filterRegistrationBean.setOrder(-1000);//order的数值越小 则优先级越高
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterRegistrationBean(TraceFilter traceFilter) {
        FilterRegistrationBean<TraceFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(traceFilter);
        filterRegistrationBean.setOrder(-10000);//order的数值越小 则优先级越高
        return filterRegistrationBean;
    }
}
