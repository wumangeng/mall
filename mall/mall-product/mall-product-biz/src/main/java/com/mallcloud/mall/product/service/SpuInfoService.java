package com.mallcloud.mall.product.service;

import com.mallcloud.mall.product.api.entity.SpuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.product.vo.SpuInfoSearchVO;
import com.mallcloud.mall.product.vo.SpuSaveVo;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

/**
 * <p>
 * spu信息 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface SpuInfoService extends IService<SpuInfo> {

	boolean saveSpuInfo(SpuSaveVo spuSaveVo);

	PageUtils queryPageByCondition(SpuInfoSearchVO spuInfoSearchVO);

	boolean upSpu(Long spuId);

	/**
	 * 修改spu状态
	 * */
	boolean updaSpuStatus( Long spuId, int status);

}
