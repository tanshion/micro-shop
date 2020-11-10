package com.abc1236.ms.core.authentication;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 供 {@link ExceptionTranslationFilter} 使用，处理AuthenticationException异常，即：未登录状态下访问受保护资源
 * Security默认实现 {@link org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint}
 * <p>
 *
 * @author tanshion
 * @email 843565561@qq.com
 */
@Slf4j
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        String res;
        if (exception instanceof AuthException) {
            log.warn("认证失败:{}", exception.getMessage());
            res = JsonUtils.to(ResultEntity.fail().message(exception.getMessage()));
        } else {
            log.error("认证失败", exception);
            res = JsonUtils.to(ResultEntity.fail().message("认证失败"));
        }
        response.getWriter().write(StrUtil.trimToEmpty(res));
    }
}
