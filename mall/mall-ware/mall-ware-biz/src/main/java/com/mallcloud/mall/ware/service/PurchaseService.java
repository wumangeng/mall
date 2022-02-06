package com.mallcloud.mall.ware.service;

import com.mallcloud.mall.ware.api.entity.Purchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.ware.vo.MergeVo;
import com.mallcloud.mall.ware.vo.PurchaseDoneVo;
import com.mallcloud.mall.ware.vo.SearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

import java.util.List;

/**
 * <p>
 * 采购信息 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface PurchaseService extends IService<Purchase> {



	/**
	 * 完成采购清单
	 * */
    boolean finishPurchase(PurchaseDoneVo doneVo);

    /**
	 * 接收（领取）采购清单
	 * */
	boolean receivePurchase(List<Long> ids);

	/**
	 * 合并采购
	 * */
	boolean mergePurchase(MergeVo mergeVo);

	/**
	 * 查询未领取采购单
	 * */
	PageUtils queryPageUnreceivePurchase(SearchVO searchVO);

	/**
	 * 条件查询采购
	 * */
	PageUtils queryPage(SearchVO searchVO);
}
