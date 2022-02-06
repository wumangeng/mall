
package com.alibaba.nacos;

import cn.hutool.core.lang.Validator;
import cn.hutool.system.SystemUtil;
import com.alibaba.nacos.config.ConfigConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author nacos
 * <p>
 * nacos console 源码运行，方便开发 生产建议从官网下载最新版配置运行
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class MallNacosApplication {

	public static void main(String[] args) {
		if (initEnv()) {
			SpringApplication.run(MallNacosApplication.class, args);
		}
	}

	/**
	 * 初始化运行环境
	 */
	private static boolean initEnv() {
		// 特殊处理 window 且用户名包含中文的用户，避免RocksDB 初始化失败
		// System.setProperty(SystemUtil.USER_HOME, "/nacos-path/");
		if (SystemUtil.getOsInfo().isWindows() && Validator.hasChinese(SystemUtil.getUserInfo().getHomeDir())) {
			log.error("路径包含中文,需要打开以上注释配置指定不包含中文目录");
			return false;
		}

		System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
		System.setProperty(ConfigConstants.AUTH_ENABLED, "false");
		System.setProperty(ConfigConstants.LOG_BASEDIR, "logs");
		return true;
	}

}
