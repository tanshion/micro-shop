package com.abc1236.ms.config.web;

import com.abc1236.ms.config.mybatis.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import javax.annotation.Nonnull;

/**
 * @author tanshion
 * @email 843565561@qq.com
 * SpringMVC 枚举类型-反序列化
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class MyStringToEnumConverterFactory implements ConverterFactory<String, IEnum> {

    @Override
    public <T extends IEnum> Converter<String, T> getConverter(@Nonnull Class<T> targetType) {
        return new StringToEnum(targetType);
    }

    private static class StringToEnum<T extends Enum<T> & IEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@Nonnull String source) {
            source = source.trim();// 去除首尾空白字符
            return source.isEmpty() ? null : EnumValue.valueOf(this.enumType, source);
        }
    }


}
