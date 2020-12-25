package com.abc1236.ms.service.cms.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.config.mybatis.SqlWrapper;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Article;
import com.abc1236.ms.mapper.cms.ArticleMapper;
import com.abc1236.ms.service.cms.ArticleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMgrMapper;

    @Override
    public ResultEntity<Object> save(Article article) {
        if (article.getId() != null) {
            Article old = articleMgrMapper.selectById(article.getId());
            old.setAuthor(article.getAuthor());
            old.setContent(article.getContent());
            old.setIdChannel(article.getIdChannel());
            old.setImg(article.getImg());
            old.setTitle(article.getTitle());
            articleMgrMapper.updateById(old);
        } else {
            articleMgrMapper.insert(article);
        }
        return ResultEntity.success();
    }

    @Override
    public ResultEntity<Object> remove(Long id) {
        articleMgrMapper.deleteById(id);
        return ResultEntity.success();
    }

    @Override
    public ResultEntity<Article> get(Long id) {
        Article article = articleMgrMapper.selectById(id);
        return ResultEntity.success(article);
    }

    @Override
    public ResultEntity<Page<Article>> list(Long page, Long limit, String title, String author, String startDate, String endDate) {
        Page<Article> articlePage = SqlWrapper.query(articleMgrMapper)
            .like(StrUtil.isNotBlank(title), Article::getTitle, title)
            .eq(StrUtil.isNotBlank(author), Article::getAuthor, author)
            .ge(StrUtil.isNotBlank(startDate), Article::getCreateTime, DateUtil.parse(startDate, "yyyyMMddHHmmss"))
            .le(StrUtil.isNotBlank(startDate), Article::getCreateTime, DateUtil.parse(endDate, "yyyyMMddHHmmss"))
            .page(new Page<>(page, limit));

        return ResultEntity.success(articlePage);
    }
}
