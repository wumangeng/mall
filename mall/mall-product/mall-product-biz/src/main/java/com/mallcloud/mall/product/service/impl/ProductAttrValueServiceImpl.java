package com.mallcloud.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.product.api.entity.ProductAttrValue;
import com.mallcloud.mall.product.api.vo.SearchVO;
import com.mallcloud.mall.product.mapper.ProductAttrValueMapper;
import com.mallcloud.mall.product.service.ProductAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValue> implements ProductAttrValueService {

	@Override
	public List<ProductAttrValue> getProductAttrValueBySpuId(Long spuId) {
		return baseMapper.selectList(Wrappers.<ProductAttrValue>lambdaQuery().eq(ProductAttrValue::getSpuId,spuId));
	}

	@Override
	public PageUtils queryPage(SearchVO searchVO) {
		String searchParam = searchVO.getSearchParam();

		Page<ProductAttrValue> page;
		QueryWrapper<ProductAttrValue> queryWrapper = new QueryWrapper<>();
		if (StrUtil.isBlank(searchParam)){
			page = this.page(new Page<>(searchVO.getCurrent(), searchVO.getSize()), queryWrapper);
		}else {
			queryWrapper.and((temp)->{
				temp.eq("spu_id",searchVO.getSearchParam())
						.or().eq("attr_id",searchVO.getSearchParam())
						.or().like("attr_name",searchVO.getSearchParam())
						.or().like("attr_value",searchVO.getSearchParam());
			});
			page = this.page(new Page<>(searchVO.getCurrent(), searchVO.getSize()),queryWrapper);
		}
		return new PageUtils(page);
	}

	@Override
	public boolean updateSpuAttr(Long spuId, List<ProductAttrValue> entities) {
		//1、删除这个spuId之前对应的所有属性
		this.baseMapper.delete(new QueryWrapper<ProductAttrValue>().eq("spu_id",spuId));

		List<ProductAttrValue> collect = entities.stream().map(item -> {
			item.setSpuId(spuId);
			return item;
		}).collect(Collectors.toList());

		return this.saveBatch(collect);
	}

	@Override
	public List<ProductAttrValue> baseAttrListforspu(Long spuId) {
		return baseMapper.selectList(Wrappers.<ProductAttrValue>lambdaQuery().eq(ProductAttrValue::getSpuId,spuId));
	}
}
