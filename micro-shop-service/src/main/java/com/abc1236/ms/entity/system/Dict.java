package com.abc1236.ms.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "t_sys_dict")
public class Dict {
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

    private String name;

    private String num;

    private Long pid;

    private String tips;
}