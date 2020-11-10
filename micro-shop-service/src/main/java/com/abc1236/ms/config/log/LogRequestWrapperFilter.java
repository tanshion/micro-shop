package com.abc1236.ms.config.log;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.config.web.RequestWrapper;
import com.abc1236.ms.util.JsonUtils;
import com.alibaba.druid.util.StringUtils;
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
                //                        antPathMatcher.match("/**.html", requestURI) ||
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
        Map<String, List<String>> map = new HashMap<>();
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
        String parameterBodyMessage = getBodyParameterMessage(request);
        String parameterMapMessage = getParameterMapMessage(request);
        String parameterMessage;
        if (StrUtil.isAllNotBlank(parameterBodyMessage, parameterMapMessage)) {
            parameterMessage = "[" + parameterMapMessage + "," + parameterBodyMessage + "]";
        } else {
            parameterMessage = parameterBodyMessage + parameterMapMessage;
        }
        return parameterMessage;
    }

    private static String getParameterMapMessage(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtil.isNotEmpty(parameterMap)) {
            return JsonUtils.to(parameterMap);
        }
        return "";
    }


    private static String getBodyParameterMessage(HttpServletRequest httpServletRequest) {
        RequestWrapper request = RequestWrapper.getWrapper(httpServletRequest);
        if (!StringUtils.equalsIgnoreCase("application/json", request.getContentType())) {
            return "";
        }
        try {
            ServletInputStream inputStream = request.getInputStream();
            return IoUtil.read(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("读取HttpServletRequest的body参数出错", e);
        }
        return "";
    }


}
