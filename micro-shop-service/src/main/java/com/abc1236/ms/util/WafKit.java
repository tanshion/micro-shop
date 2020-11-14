/*
  Copyright (c) 2011-2014, hubin (jobob@qq.com).
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.abc1236.ms.util;

import java.util.regex.Pattern;

/**
 * Web防火墙工具类
 * <p>
 *
 * @author hubin
 * @date 2014-5-8
 */
public class WafKit {
    private final static Pattern scriptPattern1 = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private final static Pattern scriptPattern2 = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private final static Pattern scriptPattern3 = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);
    private final static Pattern scriptPattern4 = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);
    private final static Pattern scriptPattern5 = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);
    private final static Pattern scriptPattern6 = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private final static Pattern scriptPattern7 = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    private final static Pattern scriptPattern8 = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);

    /**
     * 过滤XSS脚本内容
     *
     * @param value 待处理内容
     */
    public static String stripXSS(String value) {
        String rlt = null;

        if (null != value) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            rlt = value.replaceAll("", "");

            // Avoid anything between script tags

            rlt = scriptPattern1.matcher(rlt).replaceAll("");


            // Remove any lonesome </script> tag
            rlt = scriptPattern2.matcher(rlt).replaceAll("");

            // Remove any lonesome <script ...> tag
            rlt = scriptPattern3.matcher(rlt).replaceAll("");

            // Avoid eval(...) expressions
            rlt = scriptPattern4.matcher(rlt).replaceAll("");

            // Avoid expression(...) expressions

            rlt = scriptPattern5.matcher(rlt).replaceAll("");

            // Avoid javascript:... expressions
            rlt = scriptPattern6.matcher(rlt).replaceAll("");

            // Avoid vbscript:... expressions
            rlt = scriptPattern7.matcher(rlt).replaceAll("");

            // Avoid onload= expressions
            rlt = scriptPattern8.matcher(rlt).replaceAll("");
        }

        return rlt;
    }

    /**
     * 过滤SQL注入内容
     *
     * @param value 待处理内容
     */
    public static String stripSqlInjection(String value) {
        return (null == value) ? null : value.replaceAll("('.+--)|(--)|(%7C)", ""); //value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
    }

    /**
     * 过滤SQL/XSS注入内容
     *
     * @param value 待处理内容
     */
    public static String stripSqlXSS(String value) {
        return stripXSS(stripSqlInjection(value));
    }

}
