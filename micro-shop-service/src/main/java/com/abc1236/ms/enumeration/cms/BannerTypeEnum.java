package com.abc1236.ms.enumeration.cms;

/**
 * 广告类型
 */
public enum BannerTypeEnum {

    INDEX("index"),
    NEWS("news"),
    CASE("case"),
    PRODUCT("product"),
    SOLUTION("solution");

    private final String value;

    BannerTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
