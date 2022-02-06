package com.mallcloud.mall.ware.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.ware.api.entity.WareInfo;
import com.mallcloud.mall.ware.mapper.WareInfoMapper;
import com.mallcloud.mall.ware.service.WareInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.ware.vo.SearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 仓库信息 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfo> implements WareInfoService {

	@Override
	public PageUtils queryPage(SearchVO searchVO) {
		QueryWrapper<WareInfo> wrapper = new QueryWrapper<>();
		String key = searchVO.getSearchParam();
		if(StrUtil.isNotEmpty(key)){
			wrapper.eq("id",key).or()
					.like("name",key)
					.or().like("address",key)
					.or().like("areacode",key)
					.or().like("ware_principal",key)
					.or().like("principal_phone",key);
		}
		IPage<WareInfo> page = baseMapper.selectPage(new Page<>(searchVO.getCurrentPage(),searchVO.getPageSize()),wrapper);

		return new PageUtils(page);
	}
}
