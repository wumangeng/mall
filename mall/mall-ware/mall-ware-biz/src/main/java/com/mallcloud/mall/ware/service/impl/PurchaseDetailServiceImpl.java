package com.mallcloud.mall.ware.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.ware.api.entity.PurchaseDetail;
import com.mallcloud.mall.ware.api.entity.WareInfo;
import com.mallcloud.mall.ware.mapper.PurchaseDetailMapper;
import com.mallcloud.mall.ware.service.PurchaseDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.ware.service.WareInfoService;
import com.mallcloud.mall.ware.vo.PurchaseDetailSearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetail> implements PurchaseDetailService {

	@Autowired
	private WareInfoService wareInfoService;

	@Override
	public PageUtils queryPage(PurchaseDetailSearchVO searchVO) {

		QueryWrapper<PurchaseDetail> queryWrapper = new QueryWrapper<>();

		String searchParam = searchVO.getSearchParam();
		if(StrUtil.isNotEmpty(searchParam)){
			//purchase_id  sku_id
			queryWrapper.and(w->{
				w.eq("purchase_id",searchParam).or().eq("sku_id",searchParam);
			});
		}

		Integer status =searchVO.getStatus();
		if(ObjectUtil.isNotEmpty(status)){
			//purchase_id  sku_id
			queryWrapper.eq("status",status);
		}

		Long wareId = searchVO.getWareId();
		if(ObjectUtil.isNotEmpty(wareId)){
			//purchase_id  sku_id
			queryWrapper.eq("ware_id",wareId);
		}
		Page<PurchaseDetail> page = baseMapper.selectPage(new Page<>(searchVO.getCurrentPage(),searchVO.getPageSize()), queryWrapper);
		List<PurchaseDetail> records = page.getRecords();
		records.forEach(purchaseDetail -> {
			if (purchaseDetail!=null){
				WareInfo wareInfo = wareInfoService.getById(purchaseDetail.getWareId());
				if (wareInfo!=null) purchaseDetail.setWareName(wareInfo.getName());
			}
		});
		page.setRecords(records);
		return new PageUtils(page);
	}

	@Override
	public List<PurchaseDetail> listDetailByPurchaseId(Long purchaseId) {
		return baseMapper.selectList(Wrappers.<PurchaseDetail>lambdaQuery().eq(PurchaseDetail::getPurchaseId,purchaseId));
	}
}
