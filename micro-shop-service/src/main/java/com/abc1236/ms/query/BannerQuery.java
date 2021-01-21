package com.abc1236.ms.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class BannerQuery {
    private Long id;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "创建时间/注册时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新人")
    private Long modifyBy;

    @ApiModelProperty(value = "最后更新时间")
    private Date modifyTime;

    @ApiModelProperty(value = "banner图id")
    private String idFile;

    @ApiModelProperty(value = "界面")
    private String page;

    @ApiModelProperty(value = "参数")
    private String param;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "类型")
    @NotBlank(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "点击banner跳转到url")
    private String url;
}
