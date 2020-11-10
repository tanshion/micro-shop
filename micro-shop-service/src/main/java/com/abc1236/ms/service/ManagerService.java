package com.abc1236.ms.service;

import com.abc1236.ms.entity.system.User;

public interface ManagerService {
    User findByAccount(String account);
}
