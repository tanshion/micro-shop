package com.abc1236.ms.vo.offcialsite;

import com.abc1236.ms.controller.front.officialsite.News;
import lombok.Data;

import java.util.List;

@Data
public class NewsVO {
    private BannerVO banner;
    private List<News> newsList;
}
