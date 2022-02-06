package com.mallcloud.mall.ware.api.entity;

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
 * 商品库存
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_ware_sku")
@ApiModel(value="WareSku对象", description="商品库存")
public class WareSku implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "仓库id")
    @TableField("ware_id")
    private Long wareId;

	@ApiModelProperty(value = "仓库名称")
	@TableField(exist = false)
	private String wareName;

    @ApiModelProperty(value = "库存数")
    private Integer stock;

    @ApiModelProperty(value = "sku_name")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty(value = "锁定库存")
    @TableField("stock_locked")
    private Integer stockLocked;


}
