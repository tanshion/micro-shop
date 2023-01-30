package com.abc1236.ms.controller.front.officialsite;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Article;
import com.abc1236.ms.enumeration.cms.BannerTypeEnum;
import com.abc1236.ms.enumeration.cms.ChannelEnum;
import com.abc1236.ms.service.cms.ArticleService;
import com.abc1236.ms.service.cms.BannerService;
import com.abc1236.ms.vo.offcialsite.BannerVO;
import com.abc1236.ms.vo.offcialsite.NewsVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Api(tags = "offcialsite")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/offcialsite/news")
public class NewsController {
    private final BannerService bannerService;
    private final ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResultEntity<NewsVO> list() {
        NewsVO newsVO = new NewsVO();
        BannerVO banner = bannerService.queryBanner(BannerTypeEnum.NEWS.getValue());
        newsVO.setBanner(banner);
        List<News> newsList = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1L, 10L, ChannelEnum.NEWS.getId());
        for (Article article : articlePage.getRecords()) {
            News news = new News();
            news.setDesc(article.getTitle());
            news.setUrl("/article?id=" + article.getId());
            news.setSrc("static/images/icon/user.png");
            newsList.add(news);
        }
        newsVO.setNewsList(newsList);
        return ResultEntity.success(newsVO);
    }
}
