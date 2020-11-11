package com.abc1236.ms.service.impl;

import com.abc1236.ms.bo.JwtUser;
import com.abc1236.ms.cache.CacheDao;
import com.abc1236.ms.client.AuthClient;
import com.abc1236.ms.core.authentication.service.TokenService;
import com.abc1236.ms.core.authentication.token.AccessToken;
import com.abc1236.ms.core.log.LogManager;
import com.abc1236.ms.core.log.LogTaskFactory;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.system.User;
import com.abc1236.ms.exception.MyAssert;
import com.abc1236.ms.exception.ServiceException;
import com.abc1236.ms.service.AccountService;
import com.abc1236.ms.service.ManagerService;
import com.abc1236.ms.util.HttpUtil;
import com.abc1236.ms.vo.Profile;
import com.abc1236.ms.vo.UserInfoVO;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private final ManagerService managerService;
    private final AuthClient authClient;
    private final TokenService tokenService;

    /**
     * 用户登录<br>
     * 1，验证没有注册<br>
     * 2，验证密码错误<br>
     * 3，登录成功
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public AccessToken login(String username, String password) {
        User user = managerService.findByAccount(username);
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
        User user = managerService.findByAccount(jwtUser.getAccount());
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
        //try {
        //    managerService.getUserById()
        //    User user = managerService.get(getIdUser(HttpUtil.getRequest()));
        //    logger.info("oldPassword:{},password:{},rePassword:{}",MD5.md5(oldPassword, user.getSalt()),password,rePassword);
        //
        //    if(!MD5.md5(oldPassword, user.getSalt()).equals(user.getPassword())){
        //        return Rets.failure("旧密码输入错误");
        //    }
        //    if(!password.equals(rePassword)){
        //        return Rets.failure("新密码前后不一致");
        //    }
        //    user.setPassword(MD5.md5(password, user.getSalt()));
        //    managerService.update(user);
        //    //清空缓存
        //    cacheDao.hset(CacheDao.SESSION,user.getAccount(),null);
        //    return Rets.success();
        //} catch (Exception e) {
        //    logger.error(e.getMessage(), e);
        //}
        //return Rets.failure("更改密码失败");

    }
}
