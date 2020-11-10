package com.abc1236.ms.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserInfoVO {

    private Set<String> permissions;
    private Profile profile;
    private String name;
    private String role;
    private List<String> roles;
}
