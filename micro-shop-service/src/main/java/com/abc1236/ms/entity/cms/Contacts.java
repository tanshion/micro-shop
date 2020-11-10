package com.abc1236.ms.entity.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_cms_contacts")
public class Contacts {
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

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "邀约人名称")
    private String userName;
}