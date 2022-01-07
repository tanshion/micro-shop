package com.abc1236.ms.vo.shop;

import com.abc1236.ms.entity.shop.ShopUser;
import lombok.Data;

@Data
public class UserDetailsVO {
    private Long cartCount;
    private Long orderCount;
    private ShopUser shopUser;

}
