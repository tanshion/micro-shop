package com.abc1236.ms.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_sys_task_log")
public class TaskLog {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "执行时间")
    private Date execAt;

    @ApiModelProperty(value = "执行结果（成功:1、失败:0)")
    private Integer execSuccess;

    private Long idTask;

    @ApiModelProperty(value = "任务名")
    private String name;

    @ApiModelProperty(value = "抛出异常")
    private String jobException;
}