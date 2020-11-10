package com.abc1236.ms.core.authentication;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author tanshion
 * @email 843565561@qq.com
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException exception)
        throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        String res;
        if (exception instanceof AuthException) {
            log.warn("登录失败:{}", exception.getMessage());
            res = JsonUtils.to(ResultEntity.fail().message(exception.getMessage()));
        } else {
            log.error("登录失败", exception);
            res = JsonUtils.to(ResultEntity.fail());
        }
        response.getWriter().write(StrUtil.trimToEmpty(res));
    }

}
