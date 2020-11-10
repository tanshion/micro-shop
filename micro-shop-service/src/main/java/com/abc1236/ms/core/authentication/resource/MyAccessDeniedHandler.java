package com.abc1236.ms.core.authentication.resource;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.core.result.ResultStatus;
import com.abc1236.ms.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        String message = "" + exception.getMessage();
        log.info("拒绝访问: {}", message);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        ResultEntity<Object> resultEntity = ResultEntity.fail(ResultStatus.ACCESS_DENIED).message(message);
        if (message.startsWith("Access token expired")) {
            resultEntity = ResultEntity.fail(ResultStatus.ACCESS_TOKEN_EXPIRED);
        }
        String res = JsonUtils.to(resultEntity);
        response.getWriter().write(StrUtil.trimToEmpty(res));
    }
}
