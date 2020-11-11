package com.abc1236.ms.service;

import com.abc1236.ms.bo.JwtUser;
import com.abc1236.ms.core.authentication.token.AccessToken;
import com.abc1236.ms.vo.UserInfoVO;

public interface AccountService {
    AccessToken login(String username, String password);

    void logout(String token);

    UserInfoVO info(JwtUser jwtUser);

    void updatePwd(String oldPassword, String password, String rePassword);
}
