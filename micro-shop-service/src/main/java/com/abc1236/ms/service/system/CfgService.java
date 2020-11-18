package com.abc1236.ms.service.system;

import com.abc1236.ms.entity.system.Cfg;

public interface CfgService {
    boolean updateById(Cfg cfg);

    Cfg getById(long id);
}
