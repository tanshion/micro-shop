package com.abc1236.ms.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_shop_address")
public class Address {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间/注册时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "详细地址")
    private String addressDetail;

    @ApiModelProperty(value = "地区编码")
    private String areaCode;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区县")
    private String district;

    @ApiModelProperty(value = "用户id")
    private Long idUser;

    @ApiModelProperty(value = "是否默认")
    private Byte isDefault;

    @ApiModelProperty(value = "是否删除")
    private Byte isDelete;

    @ApiModelProperty(value = "收件人")
    private String name;

    @ApiModelProperty(value = "邮政编码")
    private String postCode;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "联系电话")
    private String tel;
}