package com.abc1236.ms.entity.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_message")
public class Message {
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

    @ApiModelProperty(value = "接收者")
    private String receiver;

    @ApiModelProperty(value = "消息类型,0:初始,1:成功,2:失败")
    private String state;

    @ApiModelProperty(value = "模板编码")
    private String tplCode;

    @ApiModelProperty(value = "消息类型,0:短信,1:邮件")
    private String type;

    @ApiModelProperty(value = "消息内容")
    private String content;
}