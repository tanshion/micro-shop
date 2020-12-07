package com.abc1236.ms.manager.shop.impl;

import com.abc1236.ms.config.mybatis.DaoWrapper;
import com.abc1236.ms.entity.shop.Order;
import com.abc1236.ms.manager.shop.OrderManager;
import com.abc1236.ms.mapper.shop.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderManagerImpl implements OrderManager {
    private final OrderMapper orderMapper;

    @Override
    public Integer count(Long userId) {
        return DaoWrapper.query(orderMapper)
            .eq(Order::getIdUser, userId)
            .count();
    }
}
