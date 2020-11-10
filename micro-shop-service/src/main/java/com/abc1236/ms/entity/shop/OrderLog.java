package com.abc1236.ms.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_shop_order_log")
public class OrderLog {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间/注册时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "日志详情")
    private String descript;

    @ApiModelProperty(value = "所属订单id")
    private Long idOrder;
}