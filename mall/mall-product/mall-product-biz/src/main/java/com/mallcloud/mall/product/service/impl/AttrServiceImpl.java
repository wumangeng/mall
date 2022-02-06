package com.mallcloud.mall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.common.core.constant.enums.ProductTypeEnum;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import com.mallcloud.mall.product.api.entity.Attr;
import com.mallcloud.mall.product.api.entity.AttrAttrgroupRelation;
import com.mallcloud.mall.product.api.entity.AttrGroup;
import com.mallcloud.mall.product.api.entity.Category;
import com.mallcloud.mall.product.api.vo.AttrVO;
import com.mallcloud.mall.product.vo.AttrGroupSearchVO;
import com.mallcloud.mall.product.api.vo.SearchVO;
import com.mallcloud.mall.product.mapper.AttrMapper;
import com.mallcloud.mall.product.service.AttrAttrgroupRelationService;
import com.mallcloud.mall.product.service.AttrGroupService;
import com.mallcloud.mall.product.service.AttrService;
import com.mallcloud.mall.product.service.CategoryService;
import com.mallcloud.mall.product.api.vo.AttrResVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

	@Autowired
	private AttrAttrgroupRelationService relationService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AttrGroupService attrGroupService;

	@Override
	public IPage queryPage(SearchVO searchVO) {
		QueryWrapper<Attr> wrapper = new QueryWrapper<>();
		if (searchVO.getSearchParam()!=null){
			wrapper.and((temp)->{
				temp.eq("catelog_id",searchVO.getSearchParam())
						.or().eq("attr_id",searchVO.getSearchParam())
						.or().like("attr_name",searchVO.getSearchParam())
						.or().like("value_select",searchVO.getSearchParam());
			});
		}
		Page<Attr> page = this.page(new Page<>(searchVO.getCurrent(), searchVO.getSize()), wrapper);
		return pageResHandle(page);
	}

	@Override
	@Transactional
	public boolean saveAttr(AttrVO attrVO) {
		Attr attr = new Attr();
		BeanUtil.copyProperties(attrVO,attr);
		baseMapper.insert(attr);

		//添加关联关系
		if( attrVO.getAttrGroupId()!=null){
			AttrAttrgroupRelation attrgroupRelation = new AttrAttrgroupRelation();
			attrgroupRelation.setAttrId(attr.getAttrId());
			attrgroupRelation.setAttrGroupId(attrVO.getAttrGroupId());
			return relationService.save(attrgroupRelation);
		}
		return true;
	}

	@Override
	public  IPage pageAll(Page page, QueryWrapper<Attr> query) {
		Page<Attr> selectPage = baseMapper.selectPage(page, query);

		return pageResHandle(selectPage);
	}

	@Override
	public IPage pageByCatelogId(SearchVO searchVO) {
		Page<Attr> attrPage = baseMapper.selectPage(new Page<>(searchVO.getCurrent(), searchVO.getSize()),
				Wrappers.<Attr>lambdaQuery().eq(Attr::getCatelogId, searchVO.getSearchParam()));
		return pageResHandle(attrPage);
	}

	@Override
	public AttrResVO getAttrById(Long attrId) {
		Attr attr = baseMapper.selectById(attrId);
		AttrResVO attrResVO = new AttrResVO();
		BeanUtil.copyProperties(attr,attrResVO);
		AttrAttrgroupRelation relation = relationService.getOne(Wrappers.<AttrAttrgroupRelation>lambdaQuery().eq(AttrAttrgroupRelation::getAttrId, attr.getAttrId()));
		if (relation!=null){
			AttrGroup attrGroup = attrGroupService.getById(relation.getAttrGroupId());
			attrResVO.setAttrGroupName(attrGroup.getAttrGroupName());
			attrResVO.setAttrGroupId(attrGroup.getAttrGroupId());
		}
		if (attr.getCatelogId()!=null){
			attrResVO.setCategoryName(categoryService.getById(attr.getCatelogId()).getName());
			attrResVO.setCatelogIdPath(categoryService.getCatelogPath(attr.getCatelogId()));
		}
		return attrResVO;
	}

	@Override
	public PageUtils queryBaseAttrPage(SearchVO searchVO, Long catelogId, String type) {
		QueryWrapper<Attr> queryWrapper = new QueryWrapper<>();

		// 0/1
		queryWrapper.eq("attr_type","base".equalsIgnoreCase(type)? ProductTypeEnum.AttrEnum.ATTR_TYPE_BASE.getCode():ProductTypeEnum.AttrEnum.ATTR_TYPE_SALE.getCode());

		if(catelogId != 0){
			queryWrapper.eq("catelog_id",catelogId);
		}

		String key = searchVO.getSearchParam();
		if(StrUtil.isNotEmpty(key)){
			//attr_id  attr_name
			queryWrapper.and((wrapper)->{
				wrapper.eq("attr_id",key).or().like("attr_name",key);
			});
		}

		IPage<Attr> page = this.page(new Page<>(searchVO.getCurrent(),searchVO.getSize()), queryWrapper);

		PageUtils pageUtils = new PageUtils(page);
		List<Attr> records = page.getRecords();
		List<AttrResVO> respVos = records.stream().map((attrEntity) -> {
			AttrResVO attrRespVo = new AttrResVO();
			BeanUtils.copyProperties(attrEntity, attrRespVo);

			//1、设置分类和分组的名字
			if(attrEntity!=null){
				AttrAttrgroupRelation attrId = relationService.getOne(new QueryWrapper<AttrAttrgroupRelation>().eq("attr_id", attrEntity.getAttrId()));
				if (attrId != null && attrId.getAttrGroupId()!=null) {
					AttrGroup attrGroupEntity = attrGroupService.getById(attrId.getAttrGroupId());
					attrRespVo.setAttrGroupName(attrGroupEntity.getAttrGroupName());
				}
			}

			Category categoryEntity = categoryService.getById(attrEntity.getCatelogId());
			if (categoryEntity != null) {
				attrRespVo.setCategoryName(categoryEntity.getName());
			}
			return attrRespVo;
		}).collect(Collectors.toList());

		pageUtils.setList(respVos);
		return pageUtils;
	}

	@Override
	public boolean updateAttrById(AttrVO attrVO) {
		Attr attr = new Attr();
		BeanUtil.copyProperties(attrVO,attr);
		baseMapper.updateById(attr);

		if (attrVO.getAttrGroupId() != null) {
			AttrAttrgroupRelation relation = new AttrAttrgroupRelation();
			relation.setAttrId(attrVO.getAttrId());
			relation.setAttrGroupId(attrVO.getAttrGroupId());

			Long count = relationService.count(new QueryWrapper<AttrAttrgroupRelation>().eq("attr_id", attr.getAttrId()));
			if(count>0){
				return relationService.update(relation,new UpdateWrapper<AttrAttrgroupRelation>().eq("attr_id",attr.getAttrId()));
			}else{
				return relationService.save(relation);
			}
		}
		return true;
	}

	@Override
	public List<Attr> getRelationAttr(Long attrgroupId) {
		List<AttrAttrgroupRelation> entities = relationService.list(new QueryWrapper<AttrAttrgroupRelation>().eq("attr_group_id", attrgroupId));

		List<Long> attrIds = entities.stream().map((attr) -> {
			return attr.getAttrId();
		}).collect(Collectors.toList());

		if(attrIds == null || attrIds.size() == 0){
			return null;
		}
		Collection<Attr> attrEntities = this.listByIds(attrIds);
		return (List<Attr>) attrEntities;
	}

	@Override
	public PageUtils getNoRelationAttr(SearchVO searchVO, Long attrGroupId) {
		//1、当前分组只能关联自己所属的分类里面的所有属性
		AttrGroup attrGroup = attrGroupService.getById(attrGroupId);
		Long catelogId = attrGroup.getCatelogId();
		//2、当前分组只能关联别的分组没有引用的属性
		//2.1)、当前分类下的其他分组
		List<AttrGroup> group = attrGroupService.list(new QueryWrapper<AttrGroup>().eq("catelog_id", catelogId));
		List<Long> collect = group.stream().map(item -> {
			return item.getAttrGroupId();
		}).collect(Collectors.toList());

		//2.2)、这些分组关联的属性
		List<AttrAttrgroupRelation> groupId = relationService.list(new QueryWrapper<AttrAttrgroupRelation>().in("attr_group_id", collect));
		List<Long> attrIds = groupId.stream().map(item -> {
			return item.getAttrId();
		}).collect(Collectors.toList());

		//2.3)、从当前分类的所有属性中移除这些属性；
		QueryWrapper<Attr> wrapper = new QueryWrapper<Attr>().eq("catelog_id", catelogId).eq("attr_type",ProductTypeEnum.AttrEnum.ATTR_TYPE_BASE.getCode());
		if(attrIds!=null && attrIds.size()>0){
			wrapper.notIn("attr_id", attrIds);
		}
		String key = searchVO.getSearchParam();
		if(StrUtil.isNotEmpty(key)){
			wrapper.and((w)->{
				w.eq("attr_id",key).or().like("attr_name",key);
			});
		}
		IPage<Attr> page = this.page(new Page<>(searchVO.getCurrent(),searchVO.getSize()), wrapper);
		return new PageUtils(page);
	}

	/**
	 * 在指定的所有属性集合里面，挑出检索属性
	 */
	@Override
	public List<Long> selectSearchAttrs(List<Long> attrIds) {
		QueryWrapper<Attr> wrapper = new QueryWrapper<>();
		wrapper.select("attr_id")
				.in("attr_id",attrIds)
				.eq("search_type",1);
		return  baseMapper.selectList(wrapper).stream().map(attr -> {
			return attr.getAttrId();
		}).collect(Collectors.toList());
	}


	private IPage<AttrResVO> pageResHandle(Page<Attr> attrPage){
		List<Attr> records = attrPage.getRecords();
		if (null!= records){
			IPage<AttrResVO> attrResVOIPage = new Page<>();
			List<AttrResVO> attrResVOS = records.stream().map((attr) -> {
				AttrResVO attrResVO = new AttrResVO();
				BeanUtil.copyProperties(attr, attrResVO);
				List<AttrAttrgroupRelation> relationList = relationService.list(Wrappers.<AttrAttrgroupRelation>lambdaQuery().eq(AttrAttrgroupRelation::getAttrId, attr.getAttrId()));
				relationList.forEach(relation->{
					AttrGroup attrGroup = attrGroupService.getById(relation.getAttrGroupId());
					if (attrGroup!=null){
						attrResVO.setAttrGroupName(attrGroup.getAttrGroupName());
						attrResVO.setAttrGroupId(attrGroup.getAttrGroupId());
						attrResVO.setCatelogIdPath(categoryService.getCatelogPath(attr.getCatelogId()));
					}
				});
				if (attr.getCatelogId()!=null) attrResVO.setCategoryName(categoryService.getById(attr.getCatelogId()).getName());
				return attrResVO;
			}).collect(Collectors.toList());

			attrPage.setRecords(null);
			BeanUtil.copyProperties(attrPage,attrResVOIPage);
			return attrResVOIPage.setRecords(attrResVOS);
		}
		return null;
	}
}
