package com.abc1236.ms.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_sys_operation_log")
public class OperationLog {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String classname;

    private Date createTime;

    private String logname;

    private String logtype;

    private String method;

    private String succeed;

    private Integer userid;

    @ApiModelProperty(value = "详细信息")
    private String message;
}