package com.abc1236.ms.controller.system;

import com.abc1236.ms.bo.JwtUser;
import com.abc1236.ms.constant.PermissionConstant;
import com.abc1236.ms.core.aop.BussinessLog;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.query.MenuQuery;
import com.abc1236.ms.service.system.MenuService;
import com.abc1236.ms.util.HttpUtil;
import com.abc1236.ms.vo.node.MenuNode;
import com.abc1236.ms.vo.node.RouterMenu;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * MenuController
 *
 * @author enilu
 * @version 2018/9/12 0012
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    //@Autowired
    //private TokenCache tokenCache;

    /**
     * 路由列表
     */
    @RequestMapping(value = "/listForRouter", method = RequestMethod.GET)
    public ResultEntity<List<RouterMenu>> listForRouter() {
        JwtUser jwtUser = HttpUtil.getJwtUser();
        List<RouterMenu> list = menuService.getSideBarMenus(jwtUser.getRoleList());
        return ResultEntity.success(list);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + PermissionConstant.MENU + "')")
    public ResultEntity<List<MenuNode>> list() {
        List<MenuNode> list = menuService.getMenus();
        return ResultEntity.success(list);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑菜单", key = "name")
    @PreAuthorize("hasAuthority('" + PermissionConstant.MENU_EDIT + "')")
    public Object save(@ModelAttribute @Valid MenuQuery menu) {
        ////判断是否存在该编号
        //if(menu.getId()==null) {
        //    String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
        //    if (StringUtil.isNotEmpty(existedMenuName)) {
        //        throw new ApplicationException(ApplicationExceptionEnum.EXISTED_THE_MENU);
        //    }
        //    menu.setStatus(MenuStatus.ENABLE.getCode());
        //}
        //
        ////设置父级菜单编号
        //menuService.menuSetPcode(menu);
        //if(menu.getId()==null){
        //    menuService.insert(menu);
        //}else {
        //    Menu old = menuService.get(menu.getId());
        //    LogObjectHolder.me().set(old);
        //    menuService.update(menu);
        //}
        return ResultEntity.success();
    }
    //
    //@RequestMapping(method = RequestMethod.DELETE)
    //@BussinessLog(value = "删除菜单", key = "id")
    //@RequiresPermissions(value = {Permission.MENU_DEL})
    //public Object remove(@RequestParam Long id) {
    //    logger.info("id:{}", id);
    //    if (id == null) {
    //        throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
    //    }
    //    //演示环境不允许删除初始化的菜单
    //    if(id.intValue()<70){
    //        return Rets.failure("演示环境不允许删除初始菜单");
    //    }
    //    //缓存菜单的名称
    //    LogObjectHolder.me().set(ConstantFactory.me().getMenuName(id));
    //    menuService.delMenuContainSubMenus(id);
    //    return Rets.success();
    //}
    //
    ///**
    // * 获取菜单树
    // */
    //@RequestMapping(value = "/menuTreeListByRoleId", method = RequestMethod.GET)
    //@RequiresPermissions(value = {Permission.MENU})
    //public Object menuTreeListByRoleId(Integer roleId) {
    //    List<Long> menuIds = menuService.getMenuIdsByRoleId(roleId);
    //    List<ZTreeNode> roleTreeList = null;
    //    if (menuIds==null||menuIds.isEmpty()) {
    //        roleTreeList = menuService.menuTreeList(null);
    //    } else {
    //        roleTreeList = menuService.menuTreeList(menuIds);
    //
    //    }
    //    List<Node> list = menuService.generateMenuTreeForRole(roleTreeList);
    //
    //    //element-ui中tree控件中如果选中父节点会默认选中所有子节点，所以这里将所有非叶子节点去掉
    //    Map<Long,ZTreeNode> map = cn.enilu.flash.utils.Lists.toMap(roleTreeList,"id");
    //    Map<Long,List<ZTreeNode>> group = cn.enilu.flash.utils.Lists.group(roleTreeList,"pId");
    //    for(Map.Entry<Long,List<ZTreeNode>> entry:group.entrySet()){
    //        if(entry.getValue().size()>1){
    //            roleTreeList.remove(map.get(entry.getKey()));
    //        }
    //    }
    //
    //    List<Long> checkedIds = Lists.newArrayList();
    //    for (ZTreeNode zTreeNode : roleTreeList) {
    //        if (zTreeNode.getChecked() != null && zTreeNode.getChecked()
    //                &&zTreeNode.getpId().intValue()!=0) {
    //            checkedIds.add(zTreeNode.getId());
    //        }
    //    }
    //    return Rets.success(Maps.newHashMap("treeData", list, "checkedIds", checkedIds));
    //}
}
