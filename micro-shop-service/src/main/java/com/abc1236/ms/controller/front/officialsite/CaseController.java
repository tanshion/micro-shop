package com.abc1236.ms.controller.front.officialsite;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Article;
import com.abc1236.ms.enumeration.cms.BannerTypeEnum;
import com.abc1236.ms.enumeration.cms.ChannelEnum;
import com.abc1236.ms.service.cms.ArticleService;
import com.abc1236.ms.service.cms.BannerService;
import com.abc1236.ms.vo.BannerVO;
import com.abc1236.ms.vo.CaseVO;
import com.abc1236.ms.vo.Product;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/offcialsite/case")
public class CaseController {
    private final BannerService bannerService;
    private final ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResultEntity<CaseVO> index() {
        CaseVO caseVO = new CaseVO();
        BannerVO banner = bannerService.queryBanner(BannerTypeEnum.CASE.getValue());
        caseVO.setBanner(banner);
        List<Product> products = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1L, 10L, ChannelEnum.PRODUCT.getId());
        for (Article article : articlePage.getRecords()) {
            products.add(new Product(article.getId(), article.getTitle(), article.getImg()));
        }
        caseVO.setCaseList(products);
        return ResultEntity.success(caseVO);

    }
}
