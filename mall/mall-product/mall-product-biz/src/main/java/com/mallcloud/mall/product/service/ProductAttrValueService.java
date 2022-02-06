package com.mallcloud.mall.product.service;

import com.mallcloud.mall.product.api.entity.ProductAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.product.api.vo.SearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface ProductAttrValueService extends IService<ProductAttrValue> {

	List<ProductAttrValue> getProductAttrValueBySpuId(Long spuId);

	PageUtils queryPage(SearchVO searchVO);

	boolean updateSpuAttr(Long spuId, List<ProductAttrValue> entities);

    List<ProductAttrValue> baseAttrListforspu(Long spuId);
}
