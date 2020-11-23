package com.abc1236.ms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan(basePackages = "com.abc1236.ms.mapper")
public class MicroShopServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroShopServiceApplication.class, args);
    }

}
