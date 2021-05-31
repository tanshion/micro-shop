package com.abc1236.ms.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new MyStringToEnumConverterFactory());
        registry.addConverterFactory(new MyIntegerToEnumConverterFactory());
        registry.addConverter(new StringToDateConverter());
    }
}
