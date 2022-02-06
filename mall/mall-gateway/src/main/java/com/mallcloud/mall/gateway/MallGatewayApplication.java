
package com.mallcloud.mall.gateway;

import com.mallcloud.mall.common.swagger.annotation.EnableMallSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关应用
 */
@EnableMallSwagger2
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MallGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallGatewayApplication.class, args);
//			SpringApplication application = new SpringApplication(MallGatewayApplication.class);
//		// 该设置方式
//		application.setWebApplicationType(WebApplicationType.REACTIVE);
//		application.run(args);
	}

}
