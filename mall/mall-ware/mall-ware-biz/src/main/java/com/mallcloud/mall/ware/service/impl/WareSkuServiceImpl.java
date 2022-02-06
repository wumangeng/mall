package com.mallcloud.mall.ware.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.product.api.entity.SkuInfo;
import com.mallcloud.mall.product.api.feign.RemoteSkuInfoService;
import com.mallcloud.mall.ware.api.entity.WareInfo;
import com.mallcloud.mall.ware.api.entity.WareSku;
import com.mallcloud.mall.ware.mapper.WareSkuMapper;
import com.mallcloud.mall.ware.service.WareInfoService;
import com.mallcloud.mall.ware.service.WareSkuService;
import com.mallcloud.mall.ware.api.vo.SkuHasStockVo;
import com.mallcloud.mall.ware.vo.SkuSearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品库存 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
@RequiredArgsConstructor
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements WareSkuService {

	private final RemoteSkuInfoService remoteSkuInfoService;
	private final WareInfoService wareInfoService;

	@Override
	public boolean addStock(Long skuId, Long wareId, Integer skuNum) {
		//1、判断如果还没有这个库存记录新增
		List<WareSku> entities = baseMapper.selectList(new QueryWrapper<WareSku>().eq("sku_id", skuId).eq("ware_id", wareId));
		if(entities == null || entities.size() == 0){
			WareSku skuEntity = new WareSku();
			skuEntity.setSkuId(skuId);
			skuEntity.setStock(skuNum);
			skuEntity.setWareId(wareId);
			skuEntity.setStockLocked(0);
			// 远程查询sku的名字，如果失败，整个事务无需回滚
			try {
				SkuInfo skuInfo =(SkuInfo) remoteSkuInfoService.getById(skuId).getData();
				if(ObjectUtil.isNotNull(skuInfo)){
					skuEntity.setSkuName(skuInfo.getSkuName());
				}
			}catch (Exception e){ }

			baseMapper.insert(skuEntity);
		}else{
			baseMapper.addStock(skuId,wareId,skuNum);
		}
		return true;
	}

	@Override
	public PageUtils queryPage(SkuSearchVO searchVO) {
		QueryWrapper<WareSku> queryWrapper = new QueryWrapper<>();
		Long skuId = searchVO.getSkuId();
		if( skuId !=null ){
			queryWrapper.eq("sku_id",skuId);
		}

		Long wareId = searchVO.getWareId();
		if(wareId!=null){
			queryWrapper.eq("ware_id",wareId);
		}

		IPage<WareSku> page = baseMapper.selectPage(new Page<>(searchVO.getCurrentPage(),searchVO.getPageSize()),queryWrapper);
		List<WareSku> records = page.getRecords();
		records.forEach(wareSku -> {
			if (wareSku.getWareId()!=null){
				WareInfo wareInfo = wareInfoService.getById(wareSku.getWareId());
				wareSku.setWareName(wareInfo.getName());
			}
		});
		page.setRecords(records);
		return new PageUtils(page);
	}

	@Override
	public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
		return skuIds.stream().map(item -> {
			Long count = baseMapper.getSkuStock(item);
			SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
			skuHasStockVo.setSkuId(item);
			skuHasStockVo.setHasStock(count == null ? false : count > 0);
			return skuHasStockVo;
		}).collect(Collectors.toList());
	}

}
