package com.abc1236.ms.service.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.abc1236.ms.bo.MenuBO;
import com.abc1236.ms.config.mybatis.wrapper.QueryChain;
import com.abc1236.ms.controller.state.MenuStatus;
import com.abc1236.ms.controller.system.LogObjectHolder;
import com.abc1236.ms.dao.mapper.system.MenuMapper;
import com.abc1236.ms.dao.system.MenuDAO;
import com.abc1236.ms.entity.system.Menu;
import com.abc1236.ms.exception.ServiceException;
import com.abc1236.ms.query.MenuQuery;
import com.abc1236.ms.service.system.MenuService;
import com.abc1236.ms.util.StringUtil;
import com.abc1236.ms.vo.MenuTreeVO;
import com.abc1236.ms.vo.node.MenuNode;
import com.abc1236.ms.vo.node.Node;
import com.abc1236.ms.vo.node.RouterMenu;
import com.abc1236.ms.vo.node.ZTreeNode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuDAO menuDAO;

    @Override
    public boolean updateById(Menu entity) {
        //cleanCache(entity);
        return super.updateById(entity);
    }

    @Override
    public boolean save(Menu entity) {
        //cleanCache(entity);
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //cleanCache(entity);
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        //cleanCache(entity);
        return super.removeByIds(idList);
    }

    /**
     * 清理缓存
     */
    private void cleanCache(Menu menu) {

    }

    /**
     * 获取左侧菜单树
     */
    @Override
    public List<RouterMenu> getSideBarMenus(List<Long> roleIds) {
        List<MenuBO> menuBOList = menuDAO.selectMenuByRoleIds(roleIds);
        List<RouterMenu> list = Optional.ofNullable(menuBOList)
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
        List<MenuBO> menuBOList = menuDAO.selectMenu();
        List<MenuNode> list = Optional.ofNullable(menuBOList)
            .orElse(new ArrayList<>()).stream()
            .map(MenuNode::new)
            .collect(Collectors.toList());
        List<MenuNode> result = generateTree(list);
        sortTree(result);
        return result;
    }

    @Override
    public void saveMenu(MenuQuery menuQuery) {
        Menu menu = BeanCopyUtils.copyBean(menuQuery, Menu.class);
        //判断是否存在该编号
        if (menu.getId() == null) {
            Menu existedMenu = new QueryChain<>(baseMapper)
                .eq(Menu::getCode, menu.getCode())
                .one();
            if (existedMenu != null) {
                throw new ServiceException("菜单编号重复，不能添加");
            }
            menuQuery.setStatus(MenuStatus.ENABLE.getCode());
        }
        //设置父级菜单编号
        menuSetPcode(menu);
        if (menuQuery.getId() == null) {
            save(menu);
        } else {
            Menu old = getById(menuQuery.getId());
            LogObjectHolder.me().set(old);
            updateById(menu);
        }
    }

    @Override
    public void removeMenu(Long id) {
        //缓存菜单的名称
        Menu menu = getById(id);
        LogObjectHolder.me().set(menu);
        delMenuContainSubMenus(id);
    }

    @Override
    public MenuTreeVO menuTreeListByRoleId(Long roleId) {
        List<Long> menuIds = menuDAO.selectMenuIdByRoleId(roleId);
        List<ZTreeNode> roleTreeList;
        if (menuIds == null || menuIds.isEmpty()) {
            roleTreeList = menuDAO.selectMenuTreeList();
        } else {
            roleTreeList = menuDAO.selectMenuTreeListInMenuIds(menuIds);
        }
        List<Node> list = generateMenuTreeForRole(roleTreeList);
        //element-ui中tree控件中如果选中父节点会默认选中所有子节点，所以这里将所有非叶子节点去掉
        Map<Long, ZTreeNode> map = CollectionUtil.toMap(roleTreeList, new HashMap<>(20), ZTreeNode::getId);
        Map<Long, List<ZTreeNode>> group = roleTreeList.stream().collect(
            Collectors.groupingBy(ZTreeNode::getpId, Collectors.mapping(zTreeNode -> zTreeNode, Collectors.toList())));
        for (Map.Entry<Long, List<ZTreeNode>> entry : group.entrySet()) {
            if (entry.getValue().size() > 1) {
                roleTreeList.remove(map.get(entry.getKey()));
            }
        }
        List<Long> checkedIds = Lists.newArrayList();
        for (ZTreeNode zTreeNode : roleTreeList) {
            if (zTreeNode.getChecked() != null && zTreeNode.getChecked()
                && zTreeNode.getpId().intValue() != 0) {
                checkedIds.add(zTreeNode.getId());
            }
        }
        MenuTreeVO menuTreeVO = new MenuTreeVO();
        menuTreeVO.setTreeData(list);
        menuTreeVO.setCheckedIds(checkedIds);
        return menuTreeVO;
    }

    private List<Node> generateMenuTreeForRole(List<ZTreeNode> list) {

        List<Node> nodes = new ArrayList<>(20);
        for (ZTreeNode menu : list) {
            Node permissionNode = new Node();
            permissionNode.setId(menu.getId());
            permissionNode.setName(menu.getName());
            permissionNode.setPid(menu.getpId());
            permissionNode.setChecked(menu.getChecked());
            nodes.add(permissionNode);
        }
        for (Node permissionNode : nodes) {
            for (Node child : nodes) {
                if (child.getPid().intValue() == permissionNode.getId().intValue()) {
                    permissionNode.getChildren().add(child);
                }
            }
        }
        List<Node> result = new ArrayList<>(20);
        for (Node node : nodes) {
            if (node.getPid().intValue() == 0) {
                result.add(node);
            }
        }
        return result;


    }


    private void delMenuContainSubMenus(Long menuId) {
        Menu menu = getById(menuId);
        //删除所有子菜单
        List<Menu> menus = new QueryChain<>(baseMapper)
            .like(Menu::getCode, "%[" + menu.getCode() + "]%")
            .list();
        List<Long> ids = Optional.ofNullable(menus)
            .orElse(new ArrayList<>()).stream()
            .map(Menu::getId)
            .collect(Collectors.toList());
        removeByIds(ids);
        //删除当前菜单
        removeById(menuId);

    }

    private void menuSetPcode(Menu menu) {
        if (StringUtil.isEmpty(menu.getPcode()) || "0".equals(menu.getPcode())) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {
            Menu pMenu = new QueryChain<>(baseMapper)
                .eq(Menu::getCode, menu.getPcode())
                .one();
            Integer pLevels = pMenu.getLevels();
            menu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menu.getCode().equals(menu.getPcode())) {
                throw new ServiceException("菜单编号和副编号不能一致");
            }

            menu.setLevels(pLevels + 1);
            menu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }
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
