package com.abc1236.ms.mapper.system;

import com.abc1236.ms.bo.MenuBO;
import com.abc1236.ms.entity.system.Menu;
import com.abc1236.ms.vo.node.ZTreeNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<MenuBO> selectMenuByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<MenuBO> selectMenu();

    List<Long> selectMenuIdByRoleId(@Param("roleId") Long roleId);

    List<ZTreeNode> selectMenuTreeList();

    List<ZTreeNode> selectMenuTreeListInMenuIds(@Param("menuIds") List<Long> menuIds);
}