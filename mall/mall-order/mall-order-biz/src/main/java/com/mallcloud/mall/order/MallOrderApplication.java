package com.mallcloud.mall.order;

import com.mallcloud.mall.common.feign.annotation.EnableMallFeignClients;
import com.mallcloud.mall.common.security.annotation.EnableMallResourceServer;
import com.mallcloud.mall.common.swagger.annotation.EnableMallSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author pig archetype
 * <p>
 * 项目启动类
 */
@EnableMallSwagger2
@EnableMallFeignClients
@EnableMallResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class MallOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class, args);
    }
}
