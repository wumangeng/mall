package com.mallcloud.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.product.api.entity.SkuInfo;
import com.mallcloud.mall.product.mapper.SkuInfoMapper;
import com.mallcloud.mall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.product.vo.SkuInfoSearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {


	@Override
	public List<SkuInfo> getSkusBySpuId(Long spuId) {
		return baseMapper.selectList(new QueryWrapper<SkuInfo>().eq("spu_id", spuId));
	}

	@Override
	public PageUtils queryPageByCondition(SkuInfoSearchVO searchVO) {
		QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<>();

		String searchParam = searchVO.getSearchParam();
		if (StrUtil.isNotEmpty(searchParam)) {
			queryWrapper.and((wrapper) -> {
				wrapper.eq("sku_id", searchParam).or().like("sku_name", searchParam);
			});
		}

		Long catelogId = searchVO.getCatalogId();
		if (catelogId!=null && catelogId != 0L ) {
			queryWrapper.eq("catalog_id", catelogId);
		}

		Long brandId = searchVO.getBrandId();
		if (brandId!=null && brandId != 0L) {
			queryWrapper.eq("brand_id", brandId);
		}

		Float min = searchVO.getMin();
		if (min!=null ) { queryWrapper.ge("price", min); }

		Float max = searchVO.getMax();
		if (max!=null) {
			try {
				BigDecimal bigDecimal = new BigDecimal(max);
				if (bigDecimal.compareTo(new BigDecimal("0")) > 0) { queryWrapper.le("price", max); }
			} catch (Exception ignored) {

			}
		}

		IPage<SkuInfo> page = baseMapper.selectPage(new Page<>(searchVO.getCurrentPage(), searchVO.getPageSize()), queryWrapper);
		return new PageUtils(page);
	}
}
