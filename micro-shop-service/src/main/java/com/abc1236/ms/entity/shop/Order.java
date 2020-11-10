package com.abc1236.ms.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_shop_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间/注册时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "管理员备注")
    private String adminMessage;

    @ApiModelProperty(value = "确认收货时间")
    private Date confirmReceivingTime;

    @ApiModelProperty(value = "收件人")
    private String consignee;

    @ApiModelProperty(value = "收件地址")
    private String consigneeAddress;

    @ApiModelProperty(value = "优惠券抵扣金额")
    private String couponPrice;

    @ApiModelProperty(value = "收货信息")
    private Long idAddress;

    @ApiModelProperty(value = "快递公司")
    private Long idExpress;

    @ApiModelProperty(value = "用户id")
    private Long idUser;

    @ApiModelProperty(value = "订单备注")
    private String message;

    @ApiModelProperty(value = "收件人电话")
    private String mobile;

    @ApiModelProperty(value = "订单号")
    private String orderSn;

    @ApiModelProperty(value = "支付流水号")
    private String payId;

    @ApiModelProperty(value = "支付状态1:未付款;2:已付款,3:支付中")
    private Integer payStatus;

    @ApiModelProperty(value = "支付成功时间")
    private String payTime;

    @ApiModelProperty(value = "实付类型:alipay,wechat")
    private String payType;

    @ApiModelProperty(value = "实付金额")
    private String realPrice;

    @ApiModelProperty(value = "配送费用")
    private String shippingAmount;

    @ApiModelProperty(value = "快递单号")
    private String shippingSn;

    @ApiModelProperty(value = "出库时间")
    private Date shippingTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "总金额")
    private String totalPrice;

    @ApiModelProperty(value = "交易金额")
    private String tradeAmount;
}