package com.mallcloud.mall.ware.service;

import com.mallcloud.mall.ware.api.entity.WareSku;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.ware.api.vo.SkuHasStockVo;
import com.mallcloud.mall.ware.vo.SkuSearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

import java.util.List;

/**
 * <p>
 * 商品库存 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface WareSkuService extends IService<WareSku> {

	/**
	 * 添加库存
	 * */
	boolean addStock(Long skuId, Long wareId, Integer skuNum);

    PageUtils queryPage(SkuSearchVO searchVO);

	/**
	 * 判断是否有库存
	 */
	List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
}
