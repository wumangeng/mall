package com.mallcloud.mall.ware;

import com.mallcloud.mall.common.core.util.RedisUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext applicationContext; // 注入WebApplicationContext
	@Autowired
	private RedisUtils redisUtils;

	@Before("")
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
	}

	@org.junit.Test
	public void test(){

		redisUtils.set("test","ceshi");
		System.err.println(redisUtils.get("test"));
	}





}
