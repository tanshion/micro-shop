package com.abc1236.ms.dao.system;

import com.abc1236.ms.bo.MenuBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDAO {
    List<MenuBO> selectMenusByRoleIds(@Param("roleIds") List<Long> roleIds);
}
