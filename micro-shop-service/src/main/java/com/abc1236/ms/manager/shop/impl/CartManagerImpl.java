package com.abc1236.ms.manager.shop.impl;

import com.abc1236.ms.config.mybatis.DaoWrapper;
import com.abc1236.ms.entity.shop.Cart;
import com.abc1236.ms.manager.shop.CartManager;
import com.abc1236.ms.mapper.shop.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartManagerImpl implements CartManager {
    private final CartMapper cartMapper;

    @Override
    public Integer count(Long userId) {
        return DaoWrapper.query(cartMapper)
            .eq(Cart::getIdUser, userId)
            .count();
    }
}
