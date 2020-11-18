package com.abc1236.ms.controller.system;

import com.abc1236.ms.bo.JwtUser;
import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.aop.BussinessLog;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.exception.ServiceException;
import com.abc1236.ms.query.MenuQuery;
import com.abc1236.ms.service.system.MenuService;
import com.abc1236.ms.util.HttpUtil;
import com.abc1236.ms.vo.MenuTreeVO;
import com.abc1236.ms.vo.node.MenuNode;
import com.abc1236.ms.vo.node.RouterMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * MenuController
 *
 * @author enilu
 * @version 2018/9/12 0012
 */
@Api(tags = "菜单")
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @ApiOperation("路由列表")
    @GetMapping(value = "/listForRouter")
    public ResultEntity<List<RouterMenu>> listForRouter() {
        JwtUser jwtUser = HttpUtil.getJwtUser();
        List<Long> roleList = jwtUser.getRoleList();
        List<RouterMenu> list = menuService.getSideBarMenus(roleList);
        return ResultEntity.success(list);
    }

    @ApiOperation("菜单列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('" + Permission.MENU + "')")
    public ResultEntity<List<MenuNode>> list() {
        List<MenuNode> list = menuService.getMenus();
        return ResultEntity.success(list);
    }

    @ApiOperation("编辑菜单")
    @PostMapping
    @BussinessLog(value = "编辑菜单", key = "name")
    @PreAuthorize("hasAuthority('" + Permission.MENU_EDIT + "')")
    public ResultEntity<String> save(@ModelAttribute @Valid MenuQuery menu) {
        menuService.saveMenu(menu);
        return ResultEntity.success();
    }

    @ApiOperation("删除菜单")
    @DeleteMapping
    @BussinessLog(value = "删除菜单", key = "id")
    @PreAuthorize("hasAuthority('" + Permission.MENU_DEL + "')")
    public ResultEntity<String> remove(@NotNull(message = "id不能为空") @RequestParam Long id) {
        //演示环境不允许删除初始化的菜单
        if (id.intValue() < 70) {
            throw new ServiceException("演示环境不允许删除初始菜单");
        }
        menuService.removeMenu(id);
        return ResultEntity.success();
    }

    @ApiOperation("获取菜单树")
    @GetMapping(value = "/menuTreeListByRoleId")
    @PreAuthorize("hasAuthority('" + Permission.MENU + "')")
    public ResultEntity<MenuTreeVO> menuTreeListByRoleId(Long roleId) {
        MenuTreeVO menuTreeVO = menuService.menuTreeListByRoleId(roleId);
        return ResultEntity.success(menuTreeVO);
    }
}
