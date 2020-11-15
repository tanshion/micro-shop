package com.abc1236.ms.service.system;

import com.abc1236.ms.vo.node.MenuNode;
import com.abc1236.ms.vo.node.RouterMenu;

import java.util.List;

public interface MenuService {
    List<RouterMenu> getSideBarMenus(List<Long> roleIds);

    List<MenuNode> getMenus();

}
