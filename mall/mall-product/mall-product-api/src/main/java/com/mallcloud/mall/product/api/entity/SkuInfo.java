package com.mallcloud.mall.product.api.entity;

import java.math.BigDecimal;
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
 * sku信息
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_sku_info")
@ApiModel(value="SkuInfo对象", description="sku信息")
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "skuId")
    @TableId(value = "sku_id")
    private Long skuId;

    @ApiModelProperty(value = "spuId")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "sku名称")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty(value = "sku介绍描述")
    @TableField("sku_desc")
    private String skuDesc;

    @ApiModelProperty(value = "所属分类id")
    @TableField("catalog_id")
    private Long catalogId;

    @ApiModelProperty(value = "品牌id")
    @TableField("brand_id")
    private Long brandId;

    @ApiModelProperty(value = "默认图片")
    @TableField("sku_default_img")
    private String skuDefaultImg;

    @ApiModelProperty(value = "标题")
    @TableField("sku_title")
    private String skuTitle;

    @ApiModelProperty(value = "副标题")
    @TableField("sku_subtitle")
    private String skuSubtitle;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "销量")
    @TableField("sale_count")
    private Long saleCount;


}
