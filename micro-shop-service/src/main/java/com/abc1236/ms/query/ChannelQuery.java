package com.abc1236.ms.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ChannelQuery {
    @NotBlank(message = "名称不能为空")
    private String name;
    @NotBlank(message = "编码不能为空")
    private String code;


    private Long id;
    private Date createTime;
    private Long createBy;
    private Date modifyTime;
    private Long modifyBy;
}
