package com.abc1236.ms.core.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@ToString
public enum ResultStatus {
    SUCCESS(20000, "操作成功"),
    FAIL(10011, "操作失败"),
    NOT_FOUND(100404, "请求资源不存在"),
    ACCESS_DENIED(10401, "拒绝访问"),
    ACCESS_TOKEN_EXPIRED(10441, "令牌失效请重新登录"),
    MISSING_SERVLET_REQUEST_PARAMETER(10019, "缺少必要参数"),
    TYPE_MISMATCH(10018, "参数类型不正确"),
    SERVER_ERROR(10017, "未知错误"),
    REQUEST_METHOD_NOT_SUPPORTED(10016, "请求方式不正确"),
    INVALID_PARAM(10015, "参数错误"),
    UNAUTHENTICATED(10012, "此操作需要登陆系统"),
    UNAUTHORISED(10014, "权限不足，无权操作"),
    VERSION_OLD(10020, "版本过旧"),
    AUTH_USERNAME_NONE(310001, "请输入账号！"),
    AUTH_PASSWORD_NONE(310002, "请输入密码！"),
    AUTH_VERIFYCODE_NONE(310003, "请输入验证码！"),
    AUTH_ACCOUNT_NOTEXISTS(310004, "账号不存在！"),
    AUTH_CREDENTIAL_ERROR(310005, "账号或密码错误！"),
    AUTH_LOGIN_ERROR(310006, "登陆过程出现异常请尝试重新操作！"),
    AUTH_LOGIN_APPLYTOKEN_FAIL(310007, "申请令牌失败！"),
    AUTH_LOGIN_TOKEN_SAVE_FAIL(310008, "存储令牌失败！"),
    AUTH_LOGOUT_FAIL(310009, "退出失败！"),
    TASK_CONFIG_ERROR(310010, "定时任务配置错误！"),
    ERROR_310009(310000, "无法获取用户信息！");

    //操作代码
    @ApiModelProperty(value = "操作代码", example = "310001", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
