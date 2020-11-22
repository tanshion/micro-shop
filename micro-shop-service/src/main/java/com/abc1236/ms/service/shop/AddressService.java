package com.abc1236.ms.service.shop;

import com.abc1236.ms.entity.shop.Address;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface AddressService {
    Page<Address> queryPage(Long idUser, Long page, Long limit);
}
