package com.mallcloud.mall.resources.api.vo;

import lombok.Data;

@Data
public class FastDFSResp {

	/**
	 * 文件在文件系统中的访问路径
	 */
	private String url;
	/**
	 * 文件校验码
	 */
	private String md5;
	/**
	 * 文件在文件系统中的存储位置
	 */
	private String path;
	/**
	 * 文件系统的地址
	 */
	private String domain;
	/**
	 * 文件场景(用户自定义)
	 */
	private String scene;
	/**
	 * 文件大小(以B为计算单位)
	 */
	private long size;
	/**
	 * 文件上传时间戳
	 */
	private long mtime;
	/**
	 * 文件场景集合
	 */
	private String[] scenes;
	/**
	 * 文件解释
	 */
	private String retmsg;
	/**
	 *
	 */
	private String retcode;
	/**
	 * 文件在文件系统中的存储位置
	 */
	private String src;

	/**
	 * 返回信息详情
	 */
	private String message;

}
