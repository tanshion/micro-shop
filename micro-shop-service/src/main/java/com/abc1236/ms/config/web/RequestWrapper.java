package com.abc1236.ms.config.web;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.*;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;
    private byte[] body;

    /**
     * @param request 请求
     */
    private RequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
        if (StrUtil.equalsIgnoreCase("application/json", request.getContentType())) {
            this.body = getRequestBody(request);
        }

    }

    public static RequestWrapper getWrapper(HttpServletRequest request) {
        if (request instanceof RequestWrapper) {
            return (RequestWrapper) request;
        } else {
            return new RequestWrapper(request);
        }
    }


    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        if (customHeaders.containsKey(name)) {
            headerValue = customHeaders.get(name);
        }
        return headerValue;
    }


    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(customHeaders.keySet());
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (customHeaders.containsKey(name)) {
            values = Collections.singletonList(customHeaders.get(name));
        }
        return Collections.enumeration(values);
    }

    private byte[] getRequestBody(HttpServletRequest request) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            IoUtil.copy(request.getInputStream(), byteArrayOutputStream);
            body = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("BodyReaderRequestWrapper get request body error", e);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                log.error("BodyReaderRequestWrapper get request body,close inputStream error", e);
            }

        }
        return body;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (body == null) {
            return super.getReader();
        }
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (body == null) {
            return super.getInputStream();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }
}