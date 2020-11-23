package com.abc1236.ms.service.shop;

import com.abc1236.ms.entity.shop.ShopUser;
import com.abc1236.ms.query.ShopUserListQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ShopUserService {
    Page<ShopUser> queryPage(ShopUserListQuery query);

    ShopUser get(Long id);
}