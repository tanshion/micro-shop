package com.abc1236.ms.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_sys_relation")
public class Relation {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long menuid;

    private Long roleid;
}