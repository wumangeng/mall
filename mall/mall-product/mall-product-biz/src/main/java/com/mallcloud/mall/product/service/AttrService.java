package com.mallcloud.mall.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.product.api.entity.Attr;
import com.mallcloud.mall.product.api.vo.AttrVO;
import com.mallcloud.mall.product.vo.AttrGroupSearchVO;
import com.mallcloud.mall.product.api.vo.SearchVO;
import com.mallcloud.mall.product.api.vo.AttrResVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface AttrService extends IService<Attr> {

    IPage queryPage(SearchVO searchVO);

	boolean saveAttr(AttrVO attrVO);

	IPage pageAll(Page page, QueryWrapper<Attr> query);

	IPage pageByCatelogId(SearchVO searchVO);

	AttrResVO getAttrById(Long attrId);

	PageUtils queryBaseAttrPage(SearchVO searchVO, Long catelogId, String type);

	boolean updateAttrById(AttrVO attrVO);

	List<Attr> getRelationAttr(Long attrgroupId);

	PageUtils getNoRelationAttr(SearchVO searchVO, Long attrGroupId);

	/**
	 * 在指定的所有属性集合里面，挑出检索属性
	 */
	List<Long> selectSearchAttrs(List<Long> attrIds);

}
