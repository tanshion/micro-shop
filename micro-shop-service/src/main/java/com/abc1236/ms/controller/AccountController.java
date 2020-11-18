package com.abc1236.ms.controller;

import com.abc1236.ms.bo.JwtUser;
import com.abc1236.ms.core.authentication.token.AccessToken;
import com.abc1236.ms.core.log.LogManager;
import com.abc1236.ms.core.log.LogTaskFactory;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.service.system.AccountService;
import com.abc1236.ms.service.system.UserService;
import com.abc1236.ms.util.HttpUtil;
import com.abc1236.ms.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "账户")
@RequiredArgsConstructor
@Slf4j
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @ApiOperation("用户登录")
    @PreAuthorize("permitAll")
    @PostMapping(value = "/login")
    public ResultEntity<AccessToken> login(@RequestParam("username") String userName,
        @RequestParam("password") String password) {
        AccessToken accessToken = accountService.login(userName, password);
        return ResultEntity.success(accessToken);
    }

    @ApiOperation("退出登录")
    @PostMapping(value = "/logout")
    public ResultEntity<String> logout() {
        String token = HttpUtil.getToken();
        accountService.logout(token);
        Long idUser = HttpUtil.getJwtUser().getId();
        LogManager.me().executeLog(LogTaskFactory.exitLog(idUser, HttpUtil.getIp()));
        return ResultEntity.success();
    }

    @ApiOperation("用户信息")
    @GetMapping(value = "/info")
    public ResultEntity<UserInfoVO> info() {
        JwtUser jwtUser = HttpUtil.getJwtUser();
        UserInfoVO userInfoVO = accountService.info(jwtUser);
        return ResultEntity.success(userInfoVO);
    }

    @ApiOperation("重置密码")
    @PostMapping(value = "/updatePwd")
    public ResultEntity<String> updatePwd(String oldPassword, String password, String rePassword) {
        JwtUser jwtUser = HttpUtil.getJwtUser();
        Long userId = jwtUser.getId();
        accountService.updatePwd(userId, oldPassword, password, rePassword);
        return ResultEntity.success();
    }
}
