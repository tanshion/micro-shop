package com.abc1236.ms.core.authentication.token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class AuthorizationUser extends User {


    /**
     * 主键ID
     */
    private Long id;
    /**
     * 账号
     */
    private String account;
    //private String password;
    private String name;
    private Long deptId;
    private String phone;
    /**
     * 角色集
     */
    private List<Long> roleList;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 角色名称集
     */
    private List<String> roleNames;
    /**
     * 角色编码
     */
    private List<String> roleCodes;
    /**
     * 资源路径
     */
    private Set<String> urls;
    /**
     * 资源编码
     */
    private Set<String> permissions;

    public AuthorizationUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
