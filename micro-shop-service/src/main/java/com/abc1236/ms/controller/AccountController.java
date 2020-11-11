package com.abc1236.ms.controller;

import com.abc1236.ms.bo.JwtUser;
import com.abc1236.ms.cache.CacheDao;
import com.abc1236.ms.core.authentication.token.AccessToken;
import com.abc1236.ms.core.log.LogManager;
import com.abc1236.ms.core.log.LogTaskFactory;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.service.AccountService;
import com.abc1236.ms.util.HttpUtil;
import com.abc1236.ms.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private final AccountService accountService;

    /**
     * 用户登录<br>
     *
     * @param userName userName
     * @param password password
     * @return AccessToken
     */
    @PermitAll
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultEntity<AccessToken> login(@RequestParam("username") String userName, @RequestParam("password") String password) {
        AccessToken accessToken = accountService.login(userName, password);
        return ResultEntity.success(accessToken);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultEntity<String> logout() {
        String token = HttpUtil.getToken();
        accountService.logout(token);
        Long idUser = HttpUtil.getJwtUser().getId();
        LogManager.me().executeLog(LogTaskFactory.exitLog(idUser, HttpUtil.getIp()));
        return ResultEntity.success();
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResultEntity<UserInfoVO> info() {
        JwtUser jwtUser = HttpUtil.getJwtUser();
        UserInfoVO userInfoVO = accountService.info(jwtUser);
        return ResultEntity.success(userInfoVO);
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public ResultEntity<String> updatePwd(String oldPassword, String password, String rePassword) {
        accountService.updatePwd(oldPassword, password, rePassword);

        return ResultEntity.success();
    }
}
