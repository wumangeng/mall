package com.mallcloud.mall.resources.controller;

import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.resources.service.ResoucesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/obtain")
@Api(tags ="资源获取接口")
public class ObtainResourcesController {

	@Autowired
	private ResoucesService resoucesService;

	@GetMapping("/getRecourseByFileName/{fileName}")
	@ApiOperation(value = "通过文件名获取资源信息", notes = "通过文件名获取资源信息")
	@SysLog("通过文件名获取资源信息" )
	public String getRecourseByFileName(@PathVariable String fileName) {
		resoucesService.getRecourseByFileName(fileName);
		return null;
	}

}
