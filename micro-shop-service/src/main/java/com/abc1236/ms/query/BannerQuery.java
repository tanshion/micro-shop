package com.abc1236.ms.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class BannerQuery {
    private Long id;
    private Date createTime;
    private Long createBy;
    private Date modifyTime;
    private Long modifyBy;

    private String url;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "类型不能为空")
    private String type;
    private String idFile;
    private String page;
    private String param;
}
