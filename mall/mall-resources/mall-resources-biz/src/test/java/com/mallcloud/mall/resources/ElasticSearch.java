package com.mallcloud.mall.resources;

import cn.hutool.json.JSONUtil;
import com.mallcloud.mall.resources.config.ElasticSearchConfig;
import com.mallcloud.mall.resources.entity.Resources;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearch {
	@Autowired
	private RestHighLevelClient client;

	@Test
	public void contextLoads() throws IOException {
		IndexRequest indexRequest = new IndexRequest("resources");

		Resources resources = new Resources();
		resources.setId(123l).setFileName("ceshi").setSrc("baidu");
		indexRequest.source(JSONUtil.toJsonStr(resources), XContentType.JSON);

		IndexResponse index = client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
		System.err.println(index);
	}
}
