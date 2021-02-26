package com.abc1236.ms.vo.offcialsite;

import com.abc1236.ms.controller.front.officialsite.News;
import lombok.Data;

import java.util.List;

@Data
public class IndexVO {
    private BannerVO banner;
    private List<News> newsList;
    private List<Product> products;
    private List<Solution> solutions;
}
