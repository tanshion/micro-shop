package com.abc1236.ms.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_sys_task")
public class Task {
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

    @ApiModelProperty(value = "是否允许并发")
    private Boolean concurrent;

    @ApiModelProperty(value = "定时规则")
    private String cron;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disabled;

    @ApiModelProperty(value = "执行时间")
    private Date execAt;

    @ApiModelProperty(value = "执行结果")
    private String execResult;

    @ApiModelProperty(value = "执行类")
    private String jobClass;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "任务名")
    private String name;

    @ApiModelProperty(value = "任务说明")
    private String note;

    @ApiModelProperty(value = "执行参数")
    private String data;
}