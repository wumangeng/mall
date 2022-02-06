
package com.mallcloud.mall.auth;

import com.mallcloud.mall.common.feign.annotation.EnableMallFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableMallFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MallAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallAuthApplication.class, args);
	}

}
