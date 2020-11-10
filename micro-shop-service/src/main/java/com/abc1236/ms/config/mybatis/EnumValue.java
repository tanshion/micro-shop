package com.abc1236.ms.config.mybatis;


import com.baomidou.mybatisplus.annotation.IEnum;

import java.util.Objects;

/**
 * @author tanshion
 * @email 843565561@qq.com
 * 自定义枚举类型基础接口
 * <p>
 * 用于扫描、序列化、反序列化实际枚举类
 */
public interface EnumValue extends IEnum {


    /**
     * 反序列化
     *
     * @param <T>      枚举类型并且实现 {@link EnumValue} 接口
     * @param enumType 实际枚举类型
     * @param value    当前值
     * @return 枚举常量
     */
    static <T extends Enum<T> & IEnum> T valueOf(Class<T> enumType, Object value) {
        if (enumType == null || value == null) {
            return null;
        }

        T[] enumConstants = enumType.getEnumConstants();
        for (T enumConstant : enumConstants) {
            Object enumValue = enumConstant.getValue();
            if (Objects.equals(enumValue, value)
                || Objects.equals(enumValue.toString(), value.toString())) {
                return enumConstant;
            }
        }

        return null;
    }

}
