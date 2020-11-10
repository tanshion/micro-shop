package com.abc1236.ms.core.authentication.service;

import com.abc1236.ms.entity.system.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {

    UserDetails getUserDetails(User user);

    User getSysUserByMobile(String mobile);

    User getSysUserByUsername(String username);
}
