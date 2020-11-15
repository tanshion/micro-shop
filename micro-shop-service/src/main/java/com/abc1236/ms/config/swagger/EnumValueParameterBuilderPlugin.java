package com.abc1236.ms.config.swagger;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.classmate.ResolvedType;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
public class EnumValueParameterBuilderPlugin implements ParameterBuilderPlugin {
    @Override
    public void apply(ParameterContext context) {
        ResolvedType parameterType = context.resolvedMethodParameter().getParameterType();
        Class<?> rawPrimaryType = parameterType.getErasedType();
        if (parameterType.isInstanceOf(IEnum.class)) {
            //获取EnumValue的value值
            IEnum[] values = (IEnum[]) rawPrimaryType.getEnumConstants();
            final List<String> displayValues = Arrays.stream(values).map(enumValue -> enumValue.getValue().toString()).collect(Collectors.toList());
            final AllowableListValues allowableListValues = new AllowableListValues(displayValues, rawPrimaryType.getTypeName());
            context.requestParameterBuilder().query(paramBuilder -> paramBuilder.enumerationFacet(
                enumerationElementFacetBuilder -> enumerationElementFacetBuilder.allowedValues(allowableListValues)));
        }

    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
