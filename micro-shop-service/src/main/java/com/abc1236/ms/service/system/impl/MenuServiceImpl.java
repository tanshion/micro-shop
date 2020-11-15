package com.abc1236.ms.service.system.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.abc1236.ms.dao.mapper.MenuMapper;
import com.abc1236.ms.dao.system.MenuDAO;
import com.abc1236.ms.service.system.MenuService;
import com.abc1236.ms.vo.node.MenuNode;
import com.abc1236.ms.vo.node.RouterMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final MenuDAO menuDAO;

    /**
     * 获取左侧菜单树
     */
    @Override
    public List<RouterMenu> getSideBarMenus(List<Long> roleIds) {
        List<RouterMenu> list = Optional.ofNullable(menuDAO.selectMenusByRoleIds(roleIds))
            .orElse(new ArrayList<>()).stream()
            .map(RouterMenu::new)
            .collect(Collectors.toList());
        List<RouterMenu> result = generateRouterTree(list);
        sortRouterTree(result);
        return result;
    }

    /**
     * 获取菜单列表
     */
    @Override
    public List<MenuNode> getMenus() {
        //List<MenuNode> list = Optional.ofNullable(menuDAO.selectMenusByRoleIds())
        //    .orElse(new ArrayList<>()).stream()
        //    .map(RouterMenu::new)
        //    .collect(Collectors.toList());
        //List<MenuNode> list = transferMenuNode(ms);
        //List<MenuNode> result = generateTree(list);
        //for (MenuNode menuNode : result) {
        //    if (!menuNode.getChildren().isEmpty()) {
        //        sortTree(menuNode.getChildren());
        //        for (MenuNode menuNode1 : menuNode.getChildren()) {
        //            if (!menuNode1.getChildren().isEmpty()) {
        //                sortTree(menuNode1.getChildren());
        //            }
        //        }
        //    }
        //}
        //sortTree(result);
        return null;
    }

    private List<MenuNode> transferMenuNode(List menus) {
        List<MenuNode> menuNodes = new ArrayList<>();
        try {
            for (int i = 0; i < menus.size(); i++) {
                Object[] source = (Object[]) menus.get(i);
                MenuNode menuNode = new MenuNode();
                menuNode.setId(Long.valueOf(source[0].toString()));
                menuNode.setIcon(String.valueOf(source[1]));
                menuNode.setParentId(Long.valueOf(source[2].toString()));
                menuNode.setName(String.valueOf(source[3]));
                menuNode.setUrl(String.valueOf(source[4]));
                menuNode.setLevels(Integer.valueOf(source[5].toString()));
                menuNode.setIsmenu(Integer.valueOf(source[6].toString()));
                menuNode.setNum(Integer.valueOf(source[7].toString()));
                menuNode.setCode(String.valueOf(source[8]));
                menuNode.setStatus(Integer.valueOf(source[9].toString()));
                if (source[10] != null) {
                    menuNode.setComponent(source[10].toString());
                }
                if ("1".equals(source[11].toString())) {
                    menuNode.setHidden(true);
                } else {
                    menuNode.setHidden(false);
                }
                menuNodes.add(menuNode);

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return menuNodes;
    }


    private void sortTree(List<MenuNode> list) {
        list.sort(Comparator.comparingInt(MenuNode::getNum));
    }

    private void sortRouterTree(List<RouterMenu> list) {
        for (RouterMenu routerMenu : list) {
            List<RouterMenu> children = routerMenu.getChildren();
            if (!children.isEmpty()) {
                sortRouterTree(children);
            }
        }
        list.sort(Comparator.comparingInt(RouterMenu::getNum));
    }

    private List<RouterMenu> generateRouterTree(List<RouterMenu> list) {
        List<RouterMenu> result = new ArrayList<>(20);
        Map<Long, RouterMenu> map = CollectionUtil.toMap(list, new HashMap<>(20), RouterMenu::getId);
        for (Map.Entry<Long, RouterMenu> entry : map.entrySet()) {
            RouterMenu menuNode = entry.getValue();

            if (menuNode.getParentId().intValue() != 0) {
                RouterMenu parentNode = map.get(menuNode.getParentId());
                if (parentNode != null) {
                    parentNode.getChildren().add(menuNode);
                }
            } else {
                result.add(menuNode);
            }
        }
        return result;

    }
}
