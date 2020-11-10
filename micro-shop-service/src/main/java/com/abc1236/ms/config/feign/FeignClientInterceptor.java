package com.abc1236.ms.config.feign;

import com.abc1236.ms.core.authentication.constant.TokenConstant;
import com.abc1236.ms.util.RequestUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author tanshion
 * @email 843565561@qq.com
 * Feign拦截器
 **/
@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(TokenConstant.HEADER_NAME_SYSTEM_INNER, TokenConstant.HEADER_VALUE_SYSTEM_INNER);
        HttpServletRequest request = RequestUtils.getRequest();
        if (request == null) {
            return;
        }
        //取出当前请求的header
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return;
        }
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            // 将header向下传递
            requestTemplate.header(headerName, headerValue);
        }
    }
}
