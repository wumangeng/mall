package com.mallcloud.mall.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mallcloud.mall.product.api.entity.SpuImages;
import com.mallcloud.mall.product.mapper.SpuImagesMapper;
import com.mallcloud.mall.product.service.SpuImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * spu图片 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImages> implements SpuImagesService {

	@Override
	public boolean saveImages(Long id, List<String> images) {
		if (CollUtil.isNotEmpty(images)){
			List<SpuImages> collect = images.stream().map(img -> {
				SpuImages spuImagesEntity = new SpuImages();
				spuImagesEntity.setSpuId(id);
				spuImagesEntity.setImgUrl(img);
				return spuImagesEntity;
			}).collect(Collectors.toList());
			this.saveBatch(collect);
		}
		return true;
	}
}
