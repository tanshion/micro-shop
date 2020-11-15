package com.abc1236.ms.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;


@Data
@TableName(value = "t_sys_menu")
public class MenuQuery {
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

    @NotBlank(message = "编号不能为空")
    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "組件配置")
    private String component;

    @ApiModelProperty(value = "是否隐藏")
    private boolean hidden;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否是菜单1:菜单,0:按钮")
    private Integer ismenu;

    @ApiModelProperty(value = "是否默认打开1:是,0:否")
    private Integer isopen;

    @ApiModelProperty(value = "级别")
    private Integer levels;

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "顺序")
    private Integer num;

    @ApiModelProperty(value = "父菜单编号")
    private String pcode;

    @ApiModelProperty(value = "递归父级菜单编号")
    private String pcodes;

    @ApiModelProperty(value = "状态1:启用,0:禁用")
    private Integer status;

    @ApiModelProperty(value = "鼠标悬停提示信息")
    private String tips;

    @ApiModelProperty(value = "链接")
    private String url;
}