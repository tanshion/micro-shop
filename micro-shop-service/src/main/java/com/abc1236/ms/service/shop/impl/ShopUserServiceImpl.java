package com.abc1236.ms.service.shop.impl;

import cn.hutool.core.date.DateUtil;
import com.abc1236.ms.config.mybatis.SqlWrapper;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.shop.ShopUser;
import com.abc1236.ms.manager.shop.CartManager;
import com.abc1236.ms.manager.shop.OrderManager;
import com.abc1236.ms.manager.shop.ShopUserManager;
import com.abc1236.ms.mapper.shop.ShopUserMapper;
import com.abc1236.ms.query.ShopUserListQuery;
import com.abc1236.ms.service.shop.ShopUserService;
import com.abc1236.ms.vo.shop.UserDetailsVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ShopUserServiceImpl implements ShopUserService {
    private final ShopUserMapper shopUserMapper;
    private final ShopUserManager shopUserManager;
    private final CartManager cartManager;
    private final OrderManager orderManager;
    @Override
    public Page<ShopUser> queryPage(ShopUserListQuery query) {
        return SqlWrapper.query(shopUserMapper)
            .eq(ShopUser::getMobile, query.getMobile())
            .eq(ShopUser::getNickName, query.getNickName())
            .ge(query.getStartRegDate() != null, ShopUser::getCreateTime,
                DateUtil.beginOfDay(query.getStartRegDate()))
            .le(query.getEndRegDate() != null, ShopUser::getCreateTime,
                DateUtil.endOfDay(query.getEndRegDate()))
            .ge(query.getStartLastLoginTime() != null, ShopUser::getLastLoginTime,
                DateUtil.beginOfDay(query.getStartLastLoginTime()))
            .le(query.getEndLastLoginTime() != null, ShopUser::getLastLoginTime,
                DateUtil.endOfDay(query.getEndLastLoginTime()))
            .page(new Page<>(query.getPage(), query.getLimit()));
    }

    @Override
    public ShopUser get(Long id) {
        return shopUserMapper.selectById(id);
    }

    @Override
    public UserDetailsVO getInfo(Long id) {
        ShopUser shopUser = shopUserManager.getShopUser(id);
        shopUser.setPassword("");
        shopUser.setSalt("");
        Integer cartCount = cartManager.count(id);
        Integer orderCount = orderManager.count(id);
        UserDetailsVO user = new UserDetailsVO();
        user.setShopUser(shopUser);
        user.setCartCount(cartCount);
        user.setOrderCount(orderCount);
        return user;
    }
}
