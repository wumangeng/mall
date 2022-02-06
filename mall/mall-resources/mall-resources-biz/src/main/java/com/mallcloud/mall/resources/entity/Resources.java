package com.mallcloud.mall.resources.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Painter
 * @since 2021-08-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rms_resources")
@ApiModel(value="Resources对象", description="")
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "文件名")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "文件场景(用户自定义)")
    private String scene;

    @ApiModelProperty(value = "文件在文件系统中的访问路径")
    private String url;

    @ApiModelProperty(value = "文件在文件系统中的存储位置")
    private String path;

    @ApiModelProperty(value = "文件在文件系统中的存储位置")
    private String src;

    @ApiModelProperty(value = "文件系统的地址")
    private String domain;

    @ApiModelProperty(value = "启用状态")
    private Integer status;

    @ApiModelProperty(value = "文件校验码")
    private String md5;

    @ApiModelProperty(value = "文件大小(以B为计算单位)")
    private Long size;

    @ApiModelProperty(value = "文件上传时间戳")
    private Long mtime;

    @ApiModelProperty(value = "文件解释")
    private String retmsg;

    @ApiModelProperty(value = "返回信息详情")
    private String message;


}
