package com.mallcloud.mall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.product.api.entity.AttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.product.vo.AttrGroupSearchVO;
import com.mallcloud.mall.product.vo.AttrGroupWithAttrsVo;
import com.mallcloud.mall.product.vo.SpuItemAttrGroupVo;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface AttrGroupService extends IService<AttrGroup> {

	Page<AttrGroup> queryPage(AttrGroupSearchVO searchVO);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);

	List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}
