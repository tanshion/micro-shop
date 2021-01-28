package com.abc1236.ms.controller.shop;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.shop.ShopUser;
import com.abc1236.ms.query.ShopUserListQuery;
import com.abc1236.ms.service.shop.ShopUserService;
import com.abc1236.ms.vo.shop.UserDetailsVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Api(tags = "商城用户管理")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/shop/user")
public class ShopUserController {
    private final ShopUserService shopUserService;

    @ApiOperation("用户列表")
    @GetMapping(value = "/list")
    public ResultEntity<Page<ShopUser>> list(@Valid ShopUserListQuery query) {
        Page<ShopUser> page = shopUserService.queryPage(query);
        return ResultEntity.success(page);
    }

    @ApiOperation("获取用户")
    @GetMapping(value = "{id}")
    public ResultEntity<ShopUser> get(@NotNull(message = "id不能为空")
    @Min(value = 1, message = "id必须为正整数") @PathVariable("id") Long id) {
        return ResultEntity.success(shopUserService.get(id));
    }

    @ApiOperation("用户详情包括购物车订单量")
    @GetMapping(value = "/info/{id}")
    public ResultEntity<UserDetailsVO> getInfo(@NotNull(message = "id不能为空")
    @Min(value = 1, message = "id必须为正整数") @PathVariable("id") Long id) {
        UserDetailsVO info = shopUserService.getInfo(id);
        return ResultEntity.success(info);


    }
}
