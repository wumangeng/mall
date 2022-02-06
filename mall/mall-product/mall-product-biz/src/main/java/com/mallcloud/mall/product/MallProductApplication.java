package com.mallcloud.mall.product;

import com.mallcloud.mall.common.feign.annotation.EnableMallFeignClients;
import com.mallcloud.mall.common.security.annotation.EnableMallResourceServer;
import com.mallcloud.mall.common.swagger.annotation.EnableMallSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableCaching   // 开启缓存注解
@EnableMallSwagger2
@EnableMallFeignClients
@EnableMallResourceServer
@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.mallcloud.mall")
public class MallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallProductApplication.class, args);
    }
}
