package com.abc1236.ms.service.system;

import com.abc1236.ms.entity.system.User;

public interface UserService {
    User findByAccount(String username);
    User findByPhone(String mobile);
    User findById(Long id);
    void updateById(User user);
}
