package com.abc1236.ms.manager.shop.impl;

import com.abc1236.ms.entity.shop.ShopUser;
import com.abc1236.ms.manager.shop.ShopUserManager;
import com.abc1236.ms.mapper.shop.ShopUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopUserManagerImpl implements ShopUserManager {
    private final ShopUserMapper shopUserMapper;
    @Override
    public ShopUser getShopUser(Long id) {
        return shopUserMapper.selectById(id);
    }
}
