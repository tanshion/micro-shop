package com.abc1236.ms.core.valid;

/**
 * @author Tanshion
 * @date 2019/10/18 3:47 下午
 */
public class ValidPattern {

    public static final String mobile = "^1\\d{10}$";
    public static final String mobile_msg = "手机号码格式不正确";
    public static final String sms_code = "^\\d{4,6}$";
    public static final String sms_code_msg = "^短信验证码必须4-6位数字";

}
