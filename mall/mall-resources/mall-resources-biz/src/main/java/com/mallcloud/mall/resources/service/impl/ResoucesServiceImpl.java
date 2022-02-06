package com.mallcloud.mall.resources.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import  com.mallcloud.mall.resources.entity.Resources;
import com.mallcloud.mall.resources.mapper.ResoucesMapper;
import com.mallcloud.mall.resources.service.ResoucesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.resources.api.vo.FastDFSResp;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-08-30
 */
@Service
public class ResoucesServiceImpl extends ServiceImpl<ResoucesMapper, Resources> implements ResoucesService {

	private final String UPLOAD_PATH="http://120.39.220.102:9090/upload";

	@Override
	public String uploadBrandLogo(MultipartFile file) {
		try {
			InputStreamResource isr = new InputStreamResource(file.getInputStream(),
					file.getOriginalFilename());

			String fileName=RandomUtil.randomString(3)+isr.getName();

			Map<String, Object> params = new HashMap<>(5);
			params.put("file", isr);
			params.put("path", "/mall/image/product/logo");
			params.put("output", "json");
			params.put("filename", fileName);
			String resp = HttpUtil.post(UPLOAD_PATH, params);

			FastDFSResp fastDFSResp = JSONUtil.toBean(resp, FastDFSResp.class);

			Resources resources = new Resources();
			BeanUtil.copyProperties(fastDFSResp,resources);
			resources.setFileName(fileName);
			resources.setScene("品牌logo");
			baseMapper.insert(resources);
			return resources.getUrl();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String uploadMemberHeader(MultipartFile file) {
		try {
			InputStreamResource isr = new InputStreamResource(file.getInputStream(),
					file.getOriginalFilename());
			String fileName=RandomUtil.randomString(3)+isr.getName();
			Map<String, Object> params = new HashMap<>();
			params.put("file", isr);
			params.put("path", "/mall/image/resources/memberHeader");
			params.put("output", "json");
			params.put("filename", fileName);
			String resp = HttpUtil.post(UPLOAD_PATH, params);
			FastDFSResp fastDFSResp = JSONUtil.toBean(resp, FastDFSResp.class);

			Resources resources = new Resources();
			BeanUtil.copyProperties(fastDFSResp,resources);
			resources.setFileName(fileName);
			resources.setScene("会员头像");
			baseMapper.insert(resources);
			return resources.getUrl();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("会员头像上传失败!");
		}
	}

	@Override
	public String uploadProductImage(MultipartFile file) {
		try {
			InputStreamResource isr = new InputStreamResource(file.getInputStream(),
					file.getOriginalFilename());
			String fileName=RandomUtil.randomString(3)+isr.getName();
			Map<String, Object> params = new HashMap<>();
			params.put("file", isr);
			params.put("path", "/mall/image/resources/product");
			params.put("output", "json");
			params.put("filename", fileName);
			String resp = HttpUtil.post(UPLOAD_PATH, params);
			FastDFSResp fastDFSResp = JSONUtil.toBean(resp, FastDFSResp.class);

			Resources resources = new Resources();
			BeanUtil.copyProperties(fastDFSResp,resources);
			resources.setFileName(fileName);
			resources.setScene("商品图片");
			baseMapper.insert(resources);
			return resources.getUrl();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("商品图片相关上传失败!");
		}
	}

	@Override
	public void getRecourseByFileName(String fileName) {

		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

		Resources resources = baseMapper.selectOne(Wrappers.<Resources>lambdaQuery().eq(Resources::getFileName, fileName));

		HttpRequest get = HttpUtil.createGet(resources.getUrl());
		HttpResponse execute = get.execute();
		InputStream inputStream = execute.bodyStream();
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.isTrue(false,"获取资源失败！");
		}
	}
}
