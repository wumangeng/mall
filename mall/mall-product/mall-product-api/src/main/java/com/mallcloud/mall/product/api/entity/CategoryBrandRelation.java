/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.mallcloud.mall.product.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 *
 * @author painter
 * @date 2021-08-11 21:13:45
 */
@Data
@TableName("pms_category_brand_relation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class CategoryBrandRelation extends Model<CategoryBrandRelation> {
private static final long serialVersionUID = 1L;

    /**
     * 关联表主键
     */
    @TableId
    @ApiModelProperty(value="关联表主键")
    private Long id;
    /**
     * 品牌id
     */
    @ApiModelProperty(value="品牌id")
    private Long brandId;
    /**
     * 品牌名
     */
    @ApiModelProperty(value="品牌名")
    private String brandName;
    /**
     * 分类id
     */
    @ApiModelProperty(value="分类id")
    private Long catelogId;
    /**
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    private String catelogName;
    }
