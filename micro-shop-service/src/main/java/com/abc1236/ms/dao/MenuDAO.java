package com.abc1236.ms.dao;

import com.abc1236.ms.entity.system.Menu;

import java.util.List;

public interface MenuDAO {
    List<Menu> selectMenuByRoleId(Long roleId);
}
