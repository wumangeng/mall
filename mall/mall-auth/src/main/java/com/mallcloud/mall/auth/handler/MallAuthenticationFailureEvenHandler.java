/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallcloud.mall.auth.handler;

import com.mallcloud.mall.admin.api.entity.SysLog;
import com.mallcloud.mall.common.core.util.SpringContextHolder;
import com.mallcloud.mall.common.log.event.SysLogEvent;
import com.mallcloud.mall.common.log.util.LogTypeEnum;
import com.mallcloud.mall.common.log.util.SysLogUtils;
import com.mallcloud.mall.common.security.handler.AbstractAuthenticationFailureEvenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Component
public class MallAuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

	/**
	 * 处理登录失败方法
	 * <p>
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication 登录的authenticationException 对象
	 */
	@Override
	public void handle(AuthenticationException authenticationException, Authentication authentication) {
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
		SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle("登录失败");
		logVo.setType(LogTypeEnum.ERROR.getType());
		logVo.setException(authenticationException.getMessage());
		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Long endTime = System.currentTimeMillis();
		logVo.setTime(endTime - startTime);
		logVo.setCreateBy(authentication.getName());
		logVo.setUpdateBy(authentication.getName());
		SpringContextHolder.publishEvent(new SysLogEvent(logVo));
	}

}
