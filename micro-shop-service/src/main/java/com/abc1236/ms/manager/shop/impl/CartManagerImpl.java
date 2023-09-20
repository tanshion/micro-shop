package com.abc1236.ms.manager.shop.impl;

import com.abc1236.ms.entity.shop.Cart;
import com.abc1236.ms.manager.shop.CartManager;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartManagerImpl implements CartManager {
    @Override
    public Long count(Long userId) {
        return Db.lambdaQuery(Cart.class)
                .eq(Cart::getIdUser, userId)
                .count();
    }
}
