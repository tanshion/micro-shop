package com.abc1236.ms.vo.shop;

import com.abc1236.ms.entity.shop.ShopUser;
import lombok.Data;

@Data
public class UserDetailsVO {
    private Integer cartCount;
    private Integer orderCount;
    private ShopUser shopUser;

}
