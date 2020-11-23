package com.abc1236.ms.service.shop;

import com.abc1236.ms.entity.shop.Category;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface CategoryService {
    Page<Category> queryPage(Long page, Long limit);
}
