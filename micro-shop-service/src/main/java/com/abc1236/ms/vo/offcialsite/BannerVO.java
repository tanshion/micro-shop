package com.abc1236.ms.vo.offcialsite;

import com.abc1236.ms.entity.cms.Banner;
import lombok.Data;

import java.util.List;

@Data
public class BannerVO {
    private Integer index = 0;
    private List<Banner> list;

}
