package com.abc1236.ms.manager.system;

import com.abc1236.ms.entity.system.User;

public interface UserManager {
    User findByAccount(String username);

    User findByPhone(String mobile);

    User findById(Long id);

    void updateById(User user);
}
