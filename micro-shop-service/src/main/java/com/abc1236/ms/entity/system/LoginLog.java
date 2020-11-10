package com.abc1236.ms.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_sys_login_log")
public class LoginLog {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private String ip;

    private String logname;

    private String message;

    private String succeed;

    private Integer userid;
}