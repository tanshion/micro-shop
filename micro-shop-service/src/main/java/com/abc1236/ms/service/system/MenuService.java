package com.abc1236.ms.service.system;

import com.abc1236.ms.entity.system.Menu;
import com.abc1236.ms.query.MenuQuery;
import com.abc1236.ms.vo.MenuTreeVO;
import com.abc1236.ms.vo.node.MenuNode;
import com.abc1236.ms.vo.node.RouterMenu;

import java.util.Collection;
import java.util.List;

public interface MenuService {
    List<RouterMenu> getSideBarMenus(List<Long> roleIds);

    List<MenuNode> getMenus();

    void saveMenu(MenuQuery menuQuery);

    void removeMenu(Long id);

    MenuTreeVO menuTreeListByRoleId(Long roleId);

    boolean updateById(Menu menu);

    boolean save(Menu menu);

    boolean removeById(Long id);

    boolean removeByIds(Collection<Long> idList);

    Menu getById(Long id);

}
