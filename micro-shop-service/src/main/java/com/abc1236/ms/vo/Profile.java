package com.abc1236.ms.vo;

import com.abc1236.ms.entity.system.User;
import lombok.Data;

import java.util.List;

@Data
public class Profile extends User {
    private String dept;
    private List<String> roles;
}
