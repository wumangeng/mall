package com.mallcloud.mall.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mall.threadpool")
@Component
@Data
public class ThreadPoolProperties {
	private Integer coreSize;
	private Integer maxSize;
	private Integer keepAliveTime;
}
