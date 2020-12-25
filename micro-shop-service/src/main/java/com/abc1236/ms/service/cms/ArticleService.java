package com.abc1236.ms.service.cms;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Article;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ArticleService {
    ResultEntity<Object> save(Article article);

    ResultEntity<Object> remove(Long id);

    ResultEntity<Article> get(Long id);

    ResultEntity<Page<Article>> list(Long page, Long limit, String title, String author, String startDate, String endDate);
}
