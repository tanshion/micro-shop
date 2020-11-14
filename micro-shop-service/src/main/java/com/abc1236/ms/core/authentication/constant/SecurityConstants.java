package com.abc1236.ms.core.authentication.constant;

public interface SecurityConstants {

    // ~ 手机验证码登录
    // ========================================================================================================

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String PARAMETER_NAME_MOBILE = "mobile";
    /**
     * 默认的手机验证码登录请求处理url
     */
    String SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    // ~ 用户名登录
    // ========================================================================================================

    /**
     * 用户名登录的用户名参数的名称
     */
    String PARAMETER_NAME_USERNAME = "username";
    /**
     * 用户名登录的密码参数的名称
     */
    String PARAMETER_NAME_PASSWORD = "password";
    /**
     * 默认的用户名登录请求处理url
     */
    String SIGN_IN_PROCESSING_URL_USERNAME = "/authentication/username";
}
