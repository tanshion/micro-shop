package com.abc1236.ms.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_shop_cart")
public class Cart {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间/注册时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "数量")
    private String count;

    @ApiModelProperty(value = "商品id")
    private Long idGoods;

    @ApiModelProperty(value = "skuId")
    private Long idSku;

    @ApiModelProperty(value = "用户id")
    private Long idUser;
}