package com.mallcloud.mall.product.service;

import com.mallcloud.mall.product.api.entity.SpuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu图片 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface SpuImagesService extends IService<SpuImages> {

    boolean saveImages(Long id, List<String> images);
}
