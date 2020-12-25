package com.abc1236.ms.controller.cms;

import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.aop.BussinessLog;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Article;
import com.abc1236.ms.service.cms.ArticleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Api(tags = "文章管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleMgrController {

    private final ArticleService articleService;

    @PostMapping
    @BussinessLog(value = "编辑文章", key = "name")
    @PreAuthorize("hasAuthority('" + Permission.ARTICLE_EDIT + "')")
    public ResultEntity<Object> save(@RequestBody Article article) {

        return articleService.save(article);

    }

    @DeleteMapping
    @BussinessLog(value = "删除文章", key = "id")
    @PreAuthorize("hasAuthority('" + Permission.ARTICLE_DEL + "')")
    public ResultEntity<Object> remove(Long id) {
        return articleService.remove(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permission.ARTICLE + "')")
    public ResultEntity<Article> get(Long id) {
        return articleService.get(id);
    }

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('" + Permission.ARTICLE + "')")
    public ResultEntity<Page<Article>> list(
        Long page, Long limit,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate
    ) {
        return articleService.list(page,limit,title, author, startDate, endDate);
    }
}
