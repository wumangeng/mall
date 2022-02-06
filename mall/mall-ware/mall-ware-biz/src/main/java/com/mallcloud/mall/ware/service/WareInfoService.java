package com.mallcloud.mall.ware.service;

import com.mallcloud.mall.ware.api.entity.WareInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.ware.vo.SearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

/**
 * <p>
 * 仓库信息 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface WareInfoService extends IService<WareInfo> {

	PageUtils queryPage(SearchVO searchVO);

}
