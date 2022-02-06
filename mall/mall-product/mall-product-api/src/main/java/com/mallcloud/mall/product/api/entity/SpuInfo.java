package com.mallcloud.mall.product.api.entity;

import java.math.BigDecimal;
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
 * spu信息
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_spu_info")
@ApiModel(value="SpuInfo对象", description="spu信息")
public class SpuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    @TableField("spu_name")
    private String spuName;

    @ApiModelProperty(value = "商品描述")
    @TableField("spu_description")
    private String spuDescription;

    @ApiModelProperty(value = "所属分类id")
    @TableField("catalog_id")
    private Long catalogId;

    @ApiModelProperty(value = "品牌id")
    @TableField("brand_id")
    private Long brandId;

	@ApiModelProperty(value = "重量(kg)")
    private BigDecimal weight;

    @ApiModelProperty(value = "上架状态[0 - 下架，1 - 已上架，2 - 已下架]")
    @TableField("publish_status")
    private Integer publishStatus;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
