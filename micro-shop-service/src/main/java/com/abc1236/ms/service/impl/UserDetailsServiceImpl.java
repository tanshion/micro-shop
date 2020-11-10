package com.abc1236.ms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.config.mybatis.wrapper.QueryChainWrapper;
import com.abc1236.ms.core.authentication.service.MyUserDetailsService;
import com.abc1236.ms.core.authentication.token.AuthorizationUser;
import com.abc1236.ms.dao.mapper.*;
import com.abc1236.ms.entity.system.*;
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

    private final UserMapper userMapper;
    private final DeptMapper deptMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final RelationMapper relationMapper;

    @Override
    public User getSysUserByUsername(String username) {
        log.debug("根据用户名查询用户");
        return new QueryChainWrapper<>(userMapper)
            .eq(User::getAccount, username)
            .one();
    }

    @Override
    public User getSysUserByMobile(String mobile) throws UsernameNotFoundException {
        log.debug("根据手机号查询用户");
        return new QueryChainWrapper<>(userMapper)
            .eq(User::getPhone, mobile)
            .one();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("根据用户名查询用户");
        User sysUser = new QueryChainWrapper<>(userMapper)
            .eq(User::getPhone, username)
            .one();
        return getUserDetails(sysUser);
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
        List<Role> roles = new QueryChainWrapper<>(roleMapper)
            .in(Role::getId, roleList)
            .list();
        Optional.ofNullable(roles).orElse(new ArrayList<>()).forEach(role -> {
            roleNameList.add(role.getName());
            roleCodeList.add(role.getTips());
        });
        List<Relation> relations = new QueryChainWrapper<>(relationMapper)
            .in(Relation::getRoleid, roleList)
            .list();
        List<Long> menuList = Optional.ofNullable(relations).orElse(new ArrayList<>()).stream()
            .map(Relation::getMenuid)
            .collect(Collectors.toList());
        List<Menu> menus = new QueryChainWrapper<>(menuMapper)
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
