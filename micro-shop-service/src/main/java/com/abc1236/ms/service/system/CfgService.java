package com.abc1236.ms.service.system;

import com.abc1236.ms.entity.system.Cfg;

public interface CfgService {
    Cfg getCgfById(long id);

    void update(Cfg cfg);
}
