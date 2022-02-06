package com.mallcloud.mall.product.service;

import com.mallcloud.mall.product.api.entity.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.product.vo.SkuInfoSearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface SkuInfoService extends IService<SkuInfo> {

	/**
	 * 查出当前spuId对应的所有sku信息,品牌的名字
	 */
    List<SkuInfo> getSkusBySpuId(Long spuId);

    /**
	 * 条件查询sku信息
	 * */
	PageUtils queryPageByCondition(SkuInfoSearchVO searchVO);
}
