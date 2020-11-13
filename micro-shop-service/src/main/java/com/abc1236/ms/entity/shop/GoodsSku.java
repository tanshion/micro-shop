package com.abc1236.ms.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_shop_goods_sku")
public class GoodsSku {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "创建时间/注册时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新人")
    private Long modifyBy;

    @ApiModelProperty(value = "最后更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "sku编码,格式:逗号分割的属性值id")
    private String code;

    @ApiModelProperty(value = "sku名称,格式:逗号分割的属性值")
    private String codeName;

    @ApiModelProperty(value = "商品id")
    private Long idGoods;

    @ApiModelProperty(value = "是否删除1:是,0:否")
    private boolean isDeleted;

    @ApiModelProperty(value = "市场价,原价")
    private String marketingPrice;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "库存")
    private Integer stock;
}