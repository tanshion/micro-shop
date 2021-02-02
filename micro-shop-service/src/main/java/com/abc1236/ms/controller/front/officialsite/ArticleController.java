package com.abc1236.ms.controller.front.officialsite;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Article;
import com.abc1236.ms.service.cms.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "offcialsite")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/offcialsite/article")
public class ArticleController {

    private final ArticleService articleService;

    @ApiOperation("获取文章")
    @GetMapping
    public ResultEntity<Article> get(Long id, String type) {
        log.info("type:{},id:{}", type, id);
        Article article = articleService.get(id);
        return ResultEntity.success(article);
    }
}
