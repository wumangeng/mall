package com.mallcloud.mall.ware.api.entity;

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
 * 
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_purchase_detail")
@ApiModel(value="PurchaseDetail对象", description="")
public class PurchaseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "采购单id")
    @TableField("purchase_id")
    private Long purchaseId;

    @ApiModelProperty(value = "采购商品id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "采购数量")
    @TableField("sku_num")
    private Integer skuNum;

    @ApiModelProperty(value = "采购金额")
    @TableField("sku_price")
    private BigDecimal skuPrice;

    @ApiModelProperty(value = "仓库id")
    @TableField("ware_id")
    private Long wareId;

	@ApiModelProperty(value = "仓库名")
	@TableField(exist = false)
	private String wareName;

    @ApiModelProperty(value = "状态[0新建，1已分配，2正在采购，3已完成，4采购失败]")
    private Integer status;


}
