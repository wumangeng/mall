package com.mallcloud.mall.resources.service;

import com.mallcloud.mall.resources.entity.Resources;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Painter
 * @since 2021-08-30
 */
public interface ResoucesService extends IService<Resources> {

    String uploadBrandLogo(MultipartFile file);

	String uploadMemberHeader(MultipartFile file);

	String uploadProductImage(MultipartFile file);

	void getRecourseByFileName(String fileName);

}
