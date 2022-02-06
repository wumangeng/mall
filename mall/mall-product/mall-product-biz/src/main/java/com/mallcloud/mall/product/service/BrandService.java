package com.mallcloud.mall.product.service;

import com.mallcloud.mall.product.api.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 品牌 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface BrandService extends IService<Brand> {

    boolean updateDetail(Brand brand);
}
