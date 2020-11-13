package com.abc1236.ms.entity.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "t_shop_goods")
public class Goods {
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

    @ApiModelProperty(value = "产品简介")
    private String descript;

    @ApiModelProperty(value = "大图相册列表,以逗号分隔")
    private String gallery;

    @ApiModelProperty(value = "类别id")
    private Long idCategory;

    @ApiModelProperty(value = "是否删除")
    private boolean isDelete;

    @ApiModelProperty(value = "是否人气商品")
    private boolean isHot;

    @ApiModelProperty(value = "是否新品推荐")
    private boolean isNew;

    @ApiModelProperty(value = "是否上架")
    private boolean isOnSale;

    @ApiModelProperty(value = "收藏数")
    private Integer likeNum;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "小图")
    private String pic;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "库存数量")
    private Integer stock;

    @ApiModelProperty(value = "产品详情")
    private String detail;
}