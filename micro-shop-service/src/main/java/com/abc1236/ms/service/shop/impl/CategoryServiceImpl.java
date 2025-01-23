package com.abc1236.ms.service.shop.impl;


import com.abc1236.ms.entity.shop.Category;
import com.abc1236.ms.exception.MyAssert;
import com.abc1236.ms.mapper.shop.CategoryMapper;
import com.abc1236.ms.service.shop.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    @Override
    public Page<Category> queryPage(Long page, Long limit) {
        return Db.lambdaQuery(Category.class)
            .page(new Page<>(page, limit));
    }

    @Override
    public List<Category> queryAll() {
        return Db.lambdaQuery(Category.class).list();
    }

    @Override
    public boolean insert(Category category) {
        return Db.save(category);
    }

    @Override
    public boolean update(Category category) {
        Category c = Db.getById(category.getId(), Category.class);
        MyAssert.notNull(c,"不存在");
        return SqlHelper.retBool(categoryMapper.updateById(category));
    }
}
