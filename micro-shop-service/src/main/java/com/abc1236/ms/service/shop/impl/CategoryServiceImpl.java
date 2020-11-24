package com.abc1236.ms.service.shop.impl;

import com.abc1236.ms.config.mybatis.DaoWrapper;
import com.abc1236.ms.entity.shop.Category;
import com.abc1236.ms.mapper.shop.CategoryMapper;
import com.abc1236.ms.service.shop.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        return DaoWrapper.query(categoryMapper)
            .page(new Page<>(page, limit));
    }

    @Override
    public List<Category> queryAll() {
        return DaoWrapper.query(categoryMapper).list();
    }
}
