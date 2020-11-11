package com.abc1236.ms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.bo.JwtUser;
import com.abc1236.ms.client.AuthClient;
import com.abc1236.ms.config.mybatis.wrapper.QueryChain;
import com.abc1236.ms.core.authentication.service.TokenService;
import com.abc1236.ms.core.authentication.token.AccessToken;
import com.abc1236.ms.core.log.LogManager;
import com.abc1236.ms.core.log.LogTaskFactory;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.dao.mapper.UserMapper;
import com.abc1236.ms.entity.system.User;
import com.abc1236.ms.exception.MyAssert;
import com.abc1236.ms.exception.ServiceException;
import com.abc1236.ms.service.AccountService;
import com.abc1236.ms.util.HttpUtil;
import com.abc1236.ms.vo.Profile;
import com.abc1236.ms.vo.UserInfoVO;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AuthClient authClient;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    /**
     * 用户登录<br>
     * 1，验证没有注册<br>
     * 2，验证密码错误<br>
     * 3，登录成功
     */
    @Override
    public AccessToken login(String username, String password) {
        User user = new QueryChain<>(userMapper)
            .eq(User::getAccount, username)
            .one();
        ResultEntity<AccessToken> resultEntity = authClient.loginByUsername(username, password);
        if (!resultEntity.isSuccess()) {
            throw new ServiceException(resultEntity.getMsg());
        }
        LogManager.me().executeLog(LogTaskFactory.loginLog(user.getId(), HttpUtil.getIp()));
        return resultEntity.getData();
    }

    @Override
    public void logout(String token) {
        tokenService.removeToken(token);
    }

    @Override
    public UserInfoVO info(JwtUser jwtUser) {
        MyAssert.notEmpty(jwtUser.getRoleList(), "该用户未配置权限");
        User user = userMapper.selectById(jwtUser.getId());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setName(jwtUser.getName());
        userInfoVO.setRole("admin");
        userInfoVO.setRoles(jwtUser.getRoleCodes());
        userInfoVO.setPermissions(jwtUser.getUrls());
        Profile profile = BeanCopyUtils.copyBean(user, Profile.class);
        profile.setDept(jwtUser.getDeptName());
        profile.setRoles(jwtUser.getRoleNames());
        userInfoVO.setProfile(profile);
        return userInfoVO;
    }

    @Override
    public void updatePwd(String oldPassword, String password, String rePassword) {
        Long id = HttpUtil.getJwtUser().getId();
        User user = userMapper.selectById(id);
        MyAssert.isTrue(passwordEncoder.matches(oldPassword, user.getPassword()), "旧密码输入错误");
        MyAssert.isTrue(StrUtil.equals(password, rePassword), "新密码前后不一致");
        user.setPassword(passwordEncoder.encode(password));
        userMapper.updateById(user);
    }
}
