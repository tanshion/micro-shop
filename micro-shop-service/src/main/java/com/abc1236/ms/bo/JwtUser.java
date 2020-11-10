package com.abc1236.ms.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
public class JwtUser {

    private String deptName;
    private String user_name;
    private Long deptId;
    private String client_id;
    private String phone;
    private String name;
    private Long id;
    private String jti;
    private String account;
    private List<String> roleCodes;
    private List<Long> roleList;
    private List<String> authorities;
    private Set<String> urls;
    private Set<String> permissions;
    private List<String> scope;
    private List<String> roleNames;
}
