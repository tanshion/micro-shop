package com.abc1236.ms.service.shop.impl;

import com.abc1236.ms.config.mybatis.DaoWrapper;
import com.abc1236.ms.entity.shop.Address;
import com.abc1236.ms.mapper.shop.AddressMapper;
import com.abc1236.ms.service.shop.AddressService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressMapper addressMapper;

    @Override
    public Page<Address> getPagedList(Long idUser, Long page, Long limit) {
        return DaoWrapper.query(addressMapper)
            .eq(Address::getIdUser, idUser)
            .page(new Page<>(page, limit));
    }
}
