package com.mallcloud.mall.ware.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.ware.api.entity.Purchase;
import com.mallcloud.mall.ware.api.entity.PurchaseDetail;
import com.mallcloud.mall.ware.api.entity.WareInfo;
import com.mallcloud.mall.ware.mapper.PurchaseMapper;
import com.mallcloud.mall.ware.service.PurchaseDetailService;
import com.mallcloud.mall.ware.service.PurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.ware.service.WareInfoService;
import com.mallcloud.mall.ware.service.WareSkuService;
import com.mallcloud.mall.ware.vo.MergeVo;
import com.mallcloud.mall.ware.vo.PurchaseDoneVo;
import com.mallcloud.mall.ware.vo.PurchaseItemDoneVo;
import com.mallcloud.mall.ware.vo.SearchVO;
import com.mallcloud.mall.common.core.constant.enums.WareTypeEnum;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 采购信息 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

	private final PurchaseDetailService detailService;
	private final WareSkuService wareSkuService;
	private final WareInfoService wareInfoService;

	@Override
	public boolean finishPurchase(PurchaseDoneVo doneVo) {

		Long id = doneVo.getId();

		//2、改变采购项的状态
		boolean flag = true;
		List<PurchaseItemDoneVo> items = doneVo.getItems();

		List<PurchaseDetail> updates = new ArrayList<>();
		for (PurchaseItemDoneVo item : items) {
			PurchaseDetail detailEntity = new PurchaseDetail();
			if(item.getStatus() == WareTypeEnum.PurchaseDetailStatusEnum.HASERROR.getCode()){
				flag = false;
				detailEntity.setStatus(item.getStatus());
			}else{
				detailEntity.setStatus(WareTypeEnum.PurchaseDetailStatusEnum.FINISH.getCode());
				//3、将成功采购的进行入库
				PurchaseDetail entity = detailService.getById(item.getItemId());
				wareSkuService.addStock(entity.getSkuId(),entity.getWareId(),entity.getSkuNum());
			}
			detailEntity.setId(item.getItemId());
			updates.add(detailEntity);
		}

		detailService.updateBatchById(updates);

		//1、改变采购单状态
		Purchase purchaseEntity = new Purchase();
		purchaseEntity.setId(id);
		purchaseEntity.setStatus(flag ? WareTypeEnum.PurchaseStatusEnum.FINISH.getCode() : WareTypeEnum.PurchaseStatusEnum.HASERROR.getCode());
		purchaseEntity.setUpdateTime(LocalDateTime.now());
		return baseMapper.updateById(purchaseEntity)>0;
	}

	@Override
	public boolean receivePurchase(List<Long> ids) {
		//1、确认当前采购单是新建或者已分配状态
		List<Purchase> collect = ids.stream().map(id -> {
			Purchase byId = this.getById(id);
			return byId;
		}).filter(item -> {
			if (item.getStatus() == WareTypeEnum.PurchaseStatusEnum.CREATED.getCode() ||
					item.getStatus() == WareTypeEnum.PurchaseStatusEnum.ASSIGNED.getCode()) {
				return true;
			}
			return false;
		}).map(item->{
			item.setStatus(WareTypeEnum.PurchaseStatusEnum.RECEIVE.getCode());
			item.setUpdateTime(LocalDateTime.now());
			return item;
		}).collect(Collectors.toList());

		//2、改变采购单的状态
		this.updateBatchById(collect);

		//3、改变采购项的状态
		collect.forEach((item)->{
			List<PurchaseDetail> entities = detailService.listDetailByPurchaseId(item.getId());
			List<PurchaseDetail> detailEntities = entities.stream().map(entity -> {
				PurchaseDetail entity1 = new PurchaseDetail();
				entity1.setId(entity.getId());
				entity1.setStatus(WareTypeEnum.PurchaseDetailStatusEnum.BUYING.getCode());
				return entity1;
			}).collect(Collectors.toList());
			detailService.updateBatchById(detailEntities);
		});
		return true;
	}

	@Transactional
	@Override
	public boolean mergePurchase(MergeVo mergeVo) {
		Long purchaseId = mergeVo.getPurchaseId();
		if(purchaseId == null){
			//1、新建一个
			Purchase purchaseEntity = new Purchase();

			purchaseEntity.setStatus(WareTypeEnum.PurchaseStatusEnum.CREATED.getCode());
			purchaseEntity.setCreateTime(LocalDateTime.now());
			purchaseEntity.setUpdateTime(LocalDateTime.now());
			this.save(purchaseEntity);
			purchaseId = purchaseEntity.getId();
		}

		//TODO 确认采购单状态是0,1才可以合并

		List<Long> items = mergeVo.getItems();
		Long finalPurchaseId = purchaseId;
		List<PurchaseDetail> collect = items.stream().map(i -> {
			PurchaseDetail detailEntity = new PurchaseDetail();

			detailEntity.setId(i);
			detailEntity.setPurchaseId(finalPurchaseId);
			detailEntity.setStatus(WareTypeEnum.PurchaseDetailStatusEnum.ASSIGNED.getCode());
			return detailEntity;
		}).collect(Collectors.toList());


		detailService.updateBatchById(collect);

		Purchase purchaseEntity = new Purchase();
		purchaseEntity.setId(purchaseId);
		purchaseEntity.setUpdateTime(LocalDateTime.now());
		return baseMapper.updateById(purchaseEntity)>0;
	}

	@Override
	public PageUtils queryPageUnreceivePurchase(SearchVO searchVO) {
		System.err.println(searchVO);
		IPage<Purchase> page = this.page(
				new Page<>(searchVO.getCurrentPage(),searchVO.getPageSize()),
				new QueryWrapper<Purchase>().eq("status",0).or().eq("status",1)
		);
		List<Purchase> records = page.getRecords();
		records.forEach(purchase -> {
			if (purchase!=null){
				WareInfo wareInfo = wareInfoService.getById(purchase.getWareId());
				if (wareInfo!=null) purchase.setWareName(wareInfo.getName());
			}
		});
		page.setRecords(records);
		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPage(SearchVO searchVO) {
		QueryWrapper<Purchase> wrapper = new QueryWrapper<>();
		String searchParam = searchVO.getSearchParam();
		if (StrUtil.isNotEmpty(searchParam)) {
			wrapper.eq("id",searchParam)
					.or().like("assignee_name",searchParam)
					.or().like("phone",searchParam);
		}
		Page<Purchase> page = baseMapper.selectPage(new Page<>(searchVO.getCurrentPage(), searchVO.getPageSize()), wrapper);
		List<Purchase> records = page.getRecords();
		records.forEach(purchase -> {
			if (purchase!=null){
				WareInfo wareInfo = wareInfoService.getById(purchase.getWareId());
				if (wareInfo!=null) purchase.setWareName(wareInfo.getName());
			}
		});
		page.setRecords(records);
		return new PageUtils(page);
	}
}
