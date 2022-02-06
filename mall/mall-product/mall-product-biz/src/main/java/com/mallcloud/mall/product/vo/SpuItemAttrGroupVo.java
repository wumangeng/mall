package com.mallcloud.mall.product.vo;

import com.mallcloud.mall.product.api.entity.Attr;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class SpuItemAttrGroupVo {

    private String groupName;

    private List<Attr> attrs;

}
