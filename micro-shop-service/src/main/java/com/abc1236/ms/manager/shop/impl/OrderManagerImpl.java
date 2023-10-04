package com.abc1236.ms.manager.shop.impl;

import com.abc1236.ms.entity.shop.Order;
import com.abc1236.ms.manager.shop.OrderManager;
import com.abc1236.ms.mapper.shop.OrderMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderManagerImpl implements OrderManager {
    private final OrderMapper orderMapper;

    @Override
    public Long count(Long userId) {
        return Db.lambdaQuery(Order.class)
            .eq(Order::getIdUser, userId)
            .count();
    }
}
