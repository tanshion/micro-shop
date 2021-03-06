package com.abc1236.ms.service.shop.impl;

import com.abc1236.ms.mapper.shop.CartMapper;
import com.abc1236.ms.service.shop.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CartServiceImpl implements CartService {
    private final CartMapper cartMapper;

}
