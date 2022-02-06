package com.mallcloud.mall.cart;

import com.mallcloud.mall.common.feign.annotation.EnableMallFeignClients;
import com.mallcloud.mall.common.security.annotation.EnableMallResourceServer;
import com.mallcloud.mall.common.swagger.annotation.EnableMallSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableMallSwagger2
@EnableMallFeignClients
@EnableMallResourceServer
@EnableDiscoveryClient
@EnableTransactionManagement
public class MallCartApplication {
	public static void main(String[] args) {
		SpringApplication.run(MallCartApplication.class, args);
	}
}
