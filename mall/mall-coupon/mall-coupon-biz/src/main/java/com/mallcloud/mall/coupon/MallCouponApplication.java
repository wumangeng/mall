package com.mallcloud.mall.coupon;

import com.mallcloud.mall.common.feign.annotation.EnableMallFeignClients;
import com.mallcloud.mall.common.security.annotation.EnableMallResourceServer;
import com.mallcloud.mall.common.swagger.annotation.EnableMallSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableMallSwagger2
@EnableMallFeignClients
@EnableMallResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class MallCouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallCouponApplication.class, args);
    }
}
