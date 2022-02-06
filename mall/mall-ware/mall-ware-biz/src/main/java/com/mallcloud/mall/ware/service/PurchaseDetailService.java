package com.mallcloud.mall.ware.service;

import com.mallcloud.mall.ware.api.entity.PurchaseDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.ware.vo.PurchaseDetailSearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface PurchaseDetailService extends IService<PurchaseDetail> {

	PageUtils queryPage(PurchaseDetailSearchVO searchVO);

    List<PurchaseDetail> listDetailByPurchaseId(Long purchaseId);
}
