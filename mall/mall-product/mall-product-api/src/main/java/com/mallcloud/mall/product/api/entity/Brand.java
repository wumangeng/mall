package com.mallcloud.mall.product.api.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_brand")
@ApiModel(value="Brand对象", description="品牌")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品牌id")
    @TableId(value = "brand_id")
    private Long brandId;

    @ApiModelProperty(value = "品牌名")
	@NotBlank(message = "品牌名不能为空")
    private String name;

    @ApiModelProperty(value = "品牌logo地址")
	@URL(message = "logo地址url错误")
    private String logo;

    @ApiModelProperty(value = "介绍")
    private String descript;

    @ApiModelProperty(value = "显示状态[0-不显示；1-显示]")
    @TableField("show_status")
    private Integer showStatus;

    @ApiModelProperty(value = "检索首字母")
    @TableField("first_letter")
	@Pattern(regexp = "/^[a-zA-Z]+$/",message = "首字母必须是字母之一")
	@NotNull
    private String firstLetter;

    @ApiModelProperty(value = "排序")
	@Min(value = 0,message = "序号不能小于0")
    private Integer sort;


}
