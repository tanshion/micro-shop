package com.abc1236.ms.controller.shop;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.shop.Address;
import com.abc1236.ms.service.shop.AddressService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "地址")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shop/address")
public class AddressController {
    private final AddressService addressService;

    @ApiOperation("地址列表")
    @GetMapping(value = "/list")
    public ResultEntity<Page<Address>> list(@RequestParam(required = false) Long idUser, Long page, Long limit) {
        Page<Address> addressPage = addressService.getPagedList(idUser, page, limit);
        return ResultEntity.success(addressPage);
    }
}
