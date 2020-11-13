package com.abc1236.ms.vo;

import com.abc1236.ms.entity.system.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Profile extends User {
    private String dept;
    private List<String> roles;
}
