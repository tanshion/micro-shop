package com.abc1236.ms.service.shop.impl;

import cn.hutool.core.date.DateUtil;
import com.abc1236.ms.config.mybatis.DaoWrapper;
import com.abc1236.ms.dao.mapper.shop.ShopUserMapper;
import com.abc1236.ms.entity.shop.ShopUser;
import com.abc1236.ms.query.ShopUserListQuery;
import com.abc1236.ms.service.shop.ShopUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ShopUserServiceImpl implements ShopUserService {
    private final ShopUserMapper shopUserMapper;

    @Override
    public Page<ShopUser> queryPage(ShopUserListQuery query) {
        return DaoWrapper.query(shopUserMapper)
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
}
