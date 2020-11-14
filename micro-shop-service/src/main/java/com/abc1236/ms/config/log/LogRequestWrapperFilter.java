package com.abc1236.ms.config.log;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.config.web.RequestWrapper;
import com.abc1236.ms.constant.http.HttpMediaType;
import com.abc1236.ms.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class LogRequestWrapperFilter extends OncePerRequestFilter {


    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest httpServletRequest, @Nonnull HttpServletResponse
        httpServletResponse, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        RequestWrapper request = RequestWrapper.getWrapper(httpServletRequest);
        if (!ignorePath(request.getRequestURI())) {
            printerRequestLog(request);
        }
        filterChain.doFilter(request, httpServletResponse);
    }

    private boolean ignorePath(String requestURI) {
        return
            antPathMatcher.match("/actuator/**", requestURI) ||
                antPathMatcher.match("/webjars/**", requestURI) ||
                antPathMatcher.match("/course/coursepic/list/**", requestURI) ||
                antPathMatcher.match("/swagger-**/**", requestURI) ||
                //antPathMatcher.match("/**.html", requestURI) ||
                antPathMatcher.match("/**.css", requestURI) ||
                antPathMatcher.match("/**.js", requestURI) ||
                antPathMatcher.match("/**.ico", requestURI) ||
                antPathMatcher.match("/doc.html", requestURI) ||
                antPathMatcher.match("/v2/api-docs/**", requestURI) ||
                antPathMatcher.match("/v3/api-docs/**", requestURI);

    }

    private void printerRequestLog(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String paramMsg = getParameterMessage(request);
        Map<String, List<String>> headers = getHeaders(request);
        log.debug("Headers: {}", headers);
        log.info("RequestURL: {}, Parameters: {}", requestURL, paramMsg);
    }

    private Map<String, List<String>> getHeaders(HttpServletRequest request) {
        Map<String, List<String>> map = new HashMap<>(20);
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return null;
        }
        List<String> names = Collections.list(headerNames);
        for (String name : names) {
            Enumeration<String> headers = request.getHeaders(name);
            ArrayList<String> headerList = Collections.list(headers);
            map.put(name, headerList);
        }
        return map;
    }

    private static String getParameterMessage(HttpServletRequest request) {
        String parameterMessage;
        String requestBody = getRequestBody(request);
        String requestParametersJson = "";
        Map<String, String> requestParameters = getRequestParameters(request);
        if (MapUtil.isNotEmpty(requestParameters)) {
            requestParametersJson = JsonUtils.to(requestParameters);
        }
        if (StrUtil.isAllNotBlank(requestBody, requestParametersJson)) {
            parameterMessage = "[" + requestParametersJson + "," + requestBody + "]";
        } else {
            parameterMessage = requestParametersJson + requestBody;
        }
        return parameterMessage;
    }

    /**
     * 获取所有请求的值
     */
    public static Map<String, String> getRequestParameters(HttpServletRequest request) {
        HashMap<String, String> values = new HashMap<>(20);
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

    private static String getRequestBody(HttpServletRequest httpServletRequest) {
        RequestWrapper request = RequestWrapper.getWrapper(httpServletRequest);
        if (StrUtil.equalsAnyIgnoreCase(request.getContentType(),HttpMediaType.APPLICATION_JSON_VALUE,
            HttpMediaType.APPLICATION_JSON_UTF8_VALUE)){
            try {
                ServletInputStream inputStream = request.getInputStream();
                return IoUtil.read(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error("读取HttpServletRequest的body参数出错", e);
            }
        }

        return "";
    }


}
