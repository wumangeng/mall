package com.mallcloud.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.product.api.entity.Attr;
import com.mallcloud.mall.product.api.entity.AttrGroup;
import com.mallcloud.mall.product.api.entity.Category;
import com.mallcloud.mall.product.vo.AttrGroupSearchVO;
import com.mallcloud.mall.product.mapper.AttrGroupMapper;
import com.mallcloud.mall.product.service.AttrGroupService;
import com.mallcloud.mall.product.service.AttrService;
import com.mallcloud.mall.product.service.CategoryService;
import com.mallcloud.mall.product.vo.AttrGroupWithAttrsVo;
import com.mallcloud.mall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AttrService attrService;

	@Override
	public Page<AttrGroup> queryPage(AttrGroupSearchVO searchVO) {
		QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>();
		if (searchVO.getCatelogId()!=null && searchVO.getCatelogId()!=0 ) {
			wrapper.eq("catelog_id",searchVO.getCatelogId());
		}
		if (searchVO.getParam()!=null){
			wrapper.and((temp)->{
				temp.eq("attr_group_id",searchVO.getParam())
						.or().like("attr_group_name",searchVO.getParam())
						.or().like("descript",searchVO.getParam());
			});
		}
		Page<AttrGroup> groupPage = this.page(new Page<>(searchVO.getCurrent(), searchVO.getSize()), wrapper);
		List<AttrGroup> records = groupPage.getRecords();

		groupPage.setRecords(
				records.stream().map(attrGroup -> {
					if (attrGroup.getCatelogId()!=null){
						Category category = categoryService.getById(attrGroup.getCatelogId());
						attrGroup.setCatelogName(category.getName());
					}
					return attrGroup;
				}).collect(Collectors.toList()));
		return groupPage;
	}

	@Override
	public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
		//1、查询分组信息
		List<AttrGroup> attrGroupEntities = this.list(new QueryWrapper<AttrGroup>().eq("catelog_id", catelogId));

		//2、查询所有属性
		return attrGroupEntities.stream().map(group -> {
			AttrGroupWithAttrsVo attrsVo = new AttrGroupWithAttrsVo();
			BeanUtils.copyProperties(group, attrsVo);
			List<Attr> attrs = attrService.getRelationAttr(attrsVo.getAttrGroupId());
			attrsVo.setAttrs(attrs);
			return attrsVo;
		}).collect(Collectors.toList());
	}

	@Override
	public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
		return null;
	}
}
