package com.mallcloud.mall.product.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品评价
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_spu_comment")
@ApiModel(value="SpuComment对象", description="商品评价")
public class SpuComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "spu_id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "商品名字")
    @TableField("spu_name")
    private String spuName;

    @ApiModelProperty(value = "会员昵称")
    @TableField("member_nick_name")
    private String memberNickName;

    @ApiModelProperty(value = "星级")
    private Boolean star;

    @ApiModelProperty(value = "会员ip")
    @TableField("member_ip")
    private String memberIp;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "显示状态[0-不显示，1-显示]")
    @TableField("show_status")
    private Boolean showStatus;

    @ApiModelProperty(value = "购买时属性组合")
    @TableField("spu_attributes")
    private String spuAttributes;

    @ApiModelProperty(value = "点赞数")
    @TableField("likes_count")
    private Integer likesCount;

    @ApiModelProperty(value = "回复数")
    @TableField("reply_count")
    private Integer replyCount;

    @ApiModelProperty(value = "评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]")
    private String resources;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "用户头像")
    @TableField("member_icon")
    private String memberIcon;

    @ApiModelProperty(value = "评论类型[0 - 对商品的直接评论，1 - 对评论的回复]")
    @TableField("comment_type")
    private Integer commentType;


}
