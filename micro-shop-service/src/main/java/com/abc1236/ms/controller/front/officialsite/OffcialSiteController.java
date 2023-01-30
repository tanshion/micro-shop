package com.abc1236.ms.controller.front.officialsite;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Article;
import com.abc1236.ms.enumeration.cms.ChannelEnum;
import com.abc1236.ms.service.cms.ArticleService;
import com.abc1236.ms.service.cms.BannerService;
import com.abc1236.ms.vo.offcialsite.BannerVO;
import com.abc1236.ms.vo.offcialsite.IndexVO;
import com.abc1236.ms.vo.offcialsite.Product;
import com.abc1236.ms.vo.offcialsite.Solution;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "offcialsite")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/offcialsite")
public class OffcialSiteController {

    private final BannerService bannerService;
    private final ArticleService articleService;

    @GetMapping
    public ResultEntity<IndexVO> index() {

        IndexVO indexVO = new IndexVO();
        BannerVO banner = bannerService.queryIndexBanner();

        indexVO.setBanner(banner);
        List<News> newsList = new ArrayList<>();
        List<Article> articles = articleService.queryIndexNews();
        for (Article article : articles) {
            News news = new News();
            news.setDesc(article.getTitle());
            news.setUrl("/article?id=" + article.getId());
            news.setSrc("static/images/icon/user.png");
            newsList.add(news);
        }

        indexVO.setNewsList(newsList);
        List<Product> products = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1L, 4L, ChannelEnum.PRODUCT.getId());
        for (Article article : articlePage.getRecords()) {
            Product product = new Product();
            product.setId(article.getId());
            product.setName(article.getTitle());
            product.setImg(article.getImg());
            products.add(product);
        }

        indexVO.setProducts(products);
        List<Solution> solutions = new ArrayList<>();
        articlePage = articleService.query(1L, 4L, ChannelEnum.SOLUTION.getId());
        for (Article article : articlePage.getRecords()) {
            Solution solution = new Solution();
            solution.setId(article.getId());
            solution.setName(article.getTitle());
            solution.setImg(article.getImg());
            solutions.add(solution);
        }

        indexVO.setSolutions(solutions);
        return ResultEntity.success(indexVO);

    }
}
