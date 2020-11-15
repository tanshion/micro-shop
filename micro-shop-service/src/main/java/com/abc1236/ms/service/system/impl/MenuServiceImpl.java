package com.abc1236.ms.service.system.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.abc1236.ms.dao.mapper.MenuMapper;
import com.abc1236.ms.dao.system.MenuDAO;
import com.abc1236.ms.service.system.MenuService;
import com.abc1236.ms.vo.node.MenuNode;
import com.abc1236.ms.vo.node.RouterMenu;
import com.google.common.collect.Lists;
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
        List<RouterMenu> list = Optional.ofNullable(menuDAO.selectMenuByRoleIds(roleIds))
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
        List<MenuNode> list = Optional.ofNullable(menuDAO.selectMenu())
            .orElse(new ArrayList<>()).stream()
            .map(MenuNode::new)
            .collect(Collectors.toList());
        List<MenuNode> result = generateTree(list);
        sortTree(result);
        return result;
    }



    private void sortTree(List<MenuNode> list) {
        for (MenuNode routerMenu : list) {
            List<MenuNode> children = routerMenu.getChildren();
            if (!children.isEmpty()) {
                sortTree(children);
            }
        }
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

    private List<MenuNode> generateTree(List<MenuNode> list) {
        List<MenuNode> result = new ArrayList<>(20);
        Map<Long, MenuNode> map = CollectionUtil.toMap(list, new HashMap<>(20), MenuNode::getId);
        for (Map.Entry<Long, MenuNode> entry : map.entrySet()) {
            MenuNode menuNode = entry.getValue();

            if (menuNode.getParentId().intValue() != 0) {
                MenuNode parentNode = map.get(menuNode.getParentId());
                parentNode.getChildren().add(menuNode);
            } else {
                result.add(menuNode);
            }
        }
        return result;

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
