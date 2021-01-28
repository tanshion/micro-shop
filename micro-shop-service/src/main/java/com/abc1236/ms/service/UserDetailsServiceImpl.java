package com.abc1236.ms.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.config.mybatis.wrapper.QueryChain;
import com.abc1236.ms.core.authentication.service.MyUserDetailsService;
import com.abc1236.ms.core.authentication.token.AuthorizationUser;
import com.abc1236.ms.entity.system.*;
import com.abc1236.ms.manager.system.UserManager;
import com.abc1236.ms.mapper.system.DeptMapper;
import com.abc1236.ms.mapper.system.MenuMapper;
import com.abc1236.ms.mapper.system.RelationMapper;
import com.abc1236.ms.mapper.system.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements MyUserDetailsService {

    private final UserManager userManager;
    private final DeptMapper deptMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final RelationMapper relationMapper;

    @Override
    public User getSysUserByUsername(String username) {
        log.debug("根据用户名查询用户");
        return userManager.findByAccount(username);
    }

    @Override
    public User getSysUserByMobile(String mobile) throws UsernameNotFoundException {
        log.debug("根据手机号查询用户");
        return userManager.findByPhone(mobile);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("根据用户名查询用户");
        User user = userManager.findByAccount(username);
        return getUserDetails(user);
    }

    @Override
    public UserDetails getUserDetails(User user) {
        //取出正确密码（hash值）
        String username = user.getAccount();
        String password = user.getPassword();
        long[] roleArray = StrUtil.splitToLong(user.getRoleid(), ",");
        List<Long> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        List<String> roleCodeList = new ArrayList<>();
        Set<String> permissions = new HashSet<>();
        Set<String> resUrls = new HashSet<>();
        CollectionUtil.addAll(roleList, roleArray);
        List<Role> roles = new QueryChain<>(roleMapper)
            .in(Role::getId, roleList)
            .list();
        Optional.ofNullable(roles).orElse(new ArrayList<>()).forEach(role -> {
            roleNameList.add(role.getName());
            roleCodeList.add(role.getTips());
        });
        List<Relation> relations = new QueryChain<>(relationMapper)
            .in(Relation::getRoleid, roleList)
            .list();
        List<Long> menuList = Optional.ofNullable(relations).orElse(new ArrayList<>()).stream()
            .map(Relation::getMenuid)
            .collect(Collectors.toList());
        List<Menu> menus = new QueryChain<>(menuMapper)
            .in(Menu::getId, menuList)
            .list();
        Optional.ofNullable(menus).orElse(new ArrayList<>()).forEach(menu -> {
            permissions.add(menu.getCode());
            resUrls.add(menu.getUrl());
        });
        Dept dept = deptMapper.selectById(user.getDeptid());
        AuthorizationUser userDetails = new AuthorizationUser(username, password,
            AuthorityUtils.createAuthorityList(permissions.toArray(new String[0])));
        userDetails.setId(user.getId());
        userDetails.setAccount(user.getAccount());
        userDetails.setName(user.getName());
        userDetails.setDeptId(user.getDeptid());
        userDetails.setPhone(user.getPhone());
        userDetails.setDeptName(dept.getFullname());
        userDetails.setRoleCodes(roleCodeList);
        userDetails.setRoleList(roleList);
        userDetails.setRoleNames(roleNameList);
        userDetails.setUrls(resUrls);
        userDetails.setPermissions(permissions);
        return userDetails;
    }


}
