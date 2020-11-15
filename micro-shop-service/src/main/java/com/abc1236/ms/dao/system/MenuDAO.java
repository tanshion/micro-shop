package com.abc1236.ms.dao.system;

import com.abc1236.ms.bo.MenuBO;
import com.abc1236.ms.vo.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDAO {
    List<MenuBO> selectMenuByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<MenuBO> selectMenu();

    List<Long> selectMenuIdByRoleId(@Param("roleId") Long roleId);

    List<ZTreeNode> selectMenuTreeList();

    List<ZTreeNode> selectMenuTreeListInMenuIds(@Param("menuIds") List<Long> menuIds);
}
