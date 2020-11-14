package com.abc1236.ms.config.jackjson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@Configuration
public class JacksonConfig {

    /**
     * 表示优先使用这个自定义mapper
     */
    @Primary
    @Bean
    public MyObjectMapper getMapper() {
        return new MyObjectMapper();
    }
}
