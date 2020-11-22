package com.abc1236.ms.service.shop.impl;

import com.abc1236.ms.config.mybatis.DaoWrapper;
import com.abc1236.ms.dao.mapper.shop.OrderMapper;
import com.abc1236.ms.entity.shop.Order;
import com.abc1236.ms.service.shop.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    @Override
    public Integer count(Long userId) {
        return DaoWrapper.query(orderMapper)
            .eq(Order::getIdUser, userId)
            .count();
    }
}
