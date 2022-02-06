package com.mallcloud.mall.resources.controller;


import com.mallcloud.mall.resources.service.ResoucesService;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-08-30
 */
@RestController
@RequestMapping("/upload")
@Api(tags ="资源上传接口")
public class UploadController {

	@Autowired
	private ResoucesService resoucesService;

	@PostMapping("/uploadBrandLogo")
	@ApiOperation(value = "上传品牌logo图片", notes = "上传品牌logo图片")
	@SysLog("上传品牌logo图片" )
	public String  uploadBrandLogo(@RequestPart MultipartFile file) {
		return resoucesService.uploadBrandLogo(file);
	}

	@PostMapping("/uploadMemberHeader")
	@ApiOperation(value = "上传会员头像", notes = "上传会员头像")
	@SysLog("上传会员头像" )
	public String  uploadMemberHeader(@RequestPart MultipartFile file) {
		return resoucesService.uploadMemberHeader(file);
	}

	@PostMapping("/uploadProduct")
	@ApiOperation(value = "上传商品图片", notes = "上传商品图片")
	@SysLog("上传商品图片" )
	public String  uploadProduct(@RequestPart MultipartFile file) {
		return resoucesService.uploadProductImage(file);
	}
}
