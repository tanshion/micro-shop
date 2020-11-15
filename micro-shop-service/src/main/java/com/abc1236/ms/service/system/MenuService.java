package com.abc1236.ms.service.system;

import com.abc1236.ms.entity.system.Menu;
import com.abc1236.ms.query.MenuQuery;
import com.abc1236.ms.vo.node.MenuNode;
import com.abc1236.ms.vo.node.RouterMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<RouterMenu> getSideBarMenus(List<Long> roleIds);

    List<MenuNode> getMenus();

    void saveMenu(MenuQuery menuQuery);

    void removeMenu(Long id);
}
