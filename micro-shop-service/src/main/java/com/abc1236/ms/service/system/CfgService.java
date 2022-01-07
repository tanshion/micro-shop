package com.abc1236.ms.service.system;

import com.abc1236.ms.entity.shop.Category;
import com.abc1236.ms.entity.system.Cfg;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface CfgService {
    boolean updateById(Cfg cfg);

    Cfg getById(long id);

    Page<Cfg> queryPage(Page<Cfg> page, String cfgName, String cfgValue);
}
