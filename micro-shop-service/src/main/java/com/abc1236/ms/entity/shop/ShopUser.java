package com.abc1236.ms.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_shop_user")
public class ShopUser {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "注册时间")
    private Date createTime;

    @ApiModelProperty(value = "性别:male;female")
    private String gender;

    @ApiModelProperty(value = "最后登陆时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码盐")
    private String salt;

    @ApiModelProperty(value = "微信头像")
    private String wechatHeadImgUrl;

    @ApiModelProperty(value = "微信昵称")
    private String wechatNickName;

    @ApiModelProperty(value = "微信OpenID")
    private String wechatOpenId;
}