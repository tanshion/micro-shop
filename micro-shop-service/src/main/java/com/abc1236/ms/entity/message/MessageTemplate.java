package com.abc1236.ms.entity.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_message_template")
public class MessageTemplate {
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

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "发送条件")
    private String cond;

    @ApiModelProperty(value = "发送者id")
    private Long idMessageSender;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "消息类型,0:短信,1:邮件")
    private String type;

    @ApiModelProperty(value = "内容")
    private String content;
}