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
public final class MyIntegerToEnumConverterFactory implements ConverterFactory<Integer, IEnum> {

    @Override
    public <T extends IEnum> Converter<Integer, T> getConverter(@Nonnull Class<T> targetType) {
        return new IntegerToEnum(targetType);
    }

    private static class IntegerToEnum<T extends Enum<T> & IEnum> implements Converter<Integer, T> {

        private final Class<T> enumType;

        IntegerToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@Nonnull Integer source) {
            return EnumValue.valueOf(this.enumType, source);
        }
    }


}
