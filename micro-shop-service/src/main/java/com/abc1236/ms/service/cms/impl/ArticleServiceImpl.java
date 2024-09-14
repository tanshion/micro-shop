package com.abc1236.ms.service.cms.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.entity.cms.Article;
import com.abc1236.ms.enumeration.cms.ChannelEnum;
import com.abc1236.ms.mapper.cms.ArticleMapper;
import com.abc1236.ms.service.cms.ArticleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMgrMapper;

    @Override
    public void save(Article article) {
        if (article.getId() != null) {
            Db.lambdaUpdate(Article.class)
                .eq(Article::getId, article.getId())
                .set(Article::getAuthor, article.getAuthor())
                .set(Article::getContent, article.getContent())
                .set(Article::getIdChannel, article.getIdChannel())
                .set(Article::getImg, article.getImg())
                .set(Article::getTitle, article.getTitle())
                .update();
        } else {
            Db.save(article);
        }
    }

    @Override
    public void remove(Long id) {
        articleMgrMapper.deleteById(id);
    }

    @Override
    public Article get(Long id) {
        Article article = articleMgrMapper.selectById(id);
        return article;
    }

    @Override
    public Page<Article> list(Long page, Long limit, String title, String author, String startDate, String endDate) {
        Page<Article> articlePage = Db.lambdaQuery(Article.class)
            .like(StrUtil.isNotBlank(title), Article::getTitle, title)
            .eq(StrUtil.isNotBlank(author), Article::getAuthor, author)
            .ge(StrUtil.isNotBlank(startDate), Article::getCreateTime, DateUtil.parse(startDate, "yyyyMMddHHmmss"))
            .le(StrUtil.isNotBlank(startDate), Article::getCreateTime, DateUtil.parse(endDate, "yyyyMMddHHmmss"))
            .page(new Page<>(page, limit));

        return articlePage;
    }

    @Override
    public Page<Article> query(Long page, Long limit, Long id) {
        Page<Article> pageList = Db.lambdaQuery(Article.class)
            .eq(Article::getIdChannel, id)
            .orderByAsc(Article::getId)
            .page(new Page<>(page, limit));
        return pageList;
    }

    @Override
    public List<Article> queryIndexNews() {
        Page<Article> page = query(1L, 5L, ChannelEnum.NEWS.getId());
        return page.getRecords();
    }
}
