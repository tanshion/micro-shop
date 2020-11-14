package com.abc1236.ms.exception;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.result.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局错误处理
 * SpringBoot默认会将异常映射到/error路径，从而根据请求方式返回html或json
 * 在这个控制器中处理/error路径的请求，将所有异常的返回值进行统一处理
 * <p>
 *
 * @author tanshion
 * @email 843565561@qq.com
 */
@Slf4j
@RestController
public class GlobalErrorController implements ErrorController {
    private static final String ERROR_PATH = "error";
    private static final int ERROR_400 = 400;
    private static final int ERROR_401 = 401;
    private static final int ERROR_403 = 403;
    private static final int ERROR_404 = 404;


    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * JSON格式异常处理
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ResultEntity<Object> errorApiHandler(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = request.getAttribute("javax.servlet.error.message").toString();
        String message = getErrMsgByStatus(statusCode);
        log.warn("errMsg: {}, {}", statusCode, errorMessage);
        ResultEntity<Object> result = ResultEntity.fail();
        if (StrUtil.isNotBlank(message)) {
            return result.message(message);
        }
        return result;
    }


    public String getErrMsgByStatus(Integer status) {
        if (status.equals(ERROR_400)) {
            return "客户端错误";
        } else if (status.equals(ERROR_401)) {
            return "身份验证不通过";
        } else if (status.equals(ERROR_403)) {
            return "访问被拒绝";
        } else if (status.equals(ERROR_404)) {
            return "访问资源不存在";
        }
        return "";
    }

}
