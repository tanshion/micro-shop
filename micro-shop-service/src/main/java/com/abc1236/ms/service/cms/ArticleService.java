package com.abc1236.ms.service.cms;

import com.abc1236.ms.entity.cms.Article;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ArticleService {
    void save(Article article);

    void remove(Long id);

    Article get(Long id);

    Page<Article> list(Long page, Long limit, String title, String author, String startDate, String endDate);
}