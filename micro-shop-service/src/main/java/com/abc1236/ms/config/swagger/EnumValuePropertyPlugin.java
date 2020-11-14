package com.abc1236.ms.config.swagger;

import com.baomidou.mybatisplus.annotation.IEnum;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
public class EnumValuePropertyPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotation = Optional.empty();

        if (context.getAnnotatedElement().isPresent()) {
            annotation = ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get());
        }
        if (context.getBeanPropertyDefinition().isPresent() && !annotation.isPresent()) {
            annotation = Annotations.findPropertyAnnotation(
                context.getBeanPropertyDefinition().get(),
                ApiModelProperty.class);
        }
        final Class<?> rawPrimaryType = context.getBeanPropertyDefinition().get().getRawPrimaryType();
        //过滤得到目标类型
        if (annotation.isPresent() && IEnum.class.isAssignableFrom(rawPrimaryType)) {
            //获取EnumValue的value值
            IEnum[] values = (IEnum[]) rawPrimaryType.getEnumConstants();
            final List<String> displayValues = Arrays.stream(values).map(enumValue ->
                enumValue.getValue().toString()).collect(Collectors.toList());
            final AllowableListValues allowableListValues = new AllowableListValues(displayValues, rawPrimaryType.getTypeName());
            //设置类型为EnumValue的value的类型
            context.getSpecificationBuilder().enumerationFacet(enumerationElementFacetBuilder ->
                enumerationElementFacetBuilder.allowedValues(allowableListValues));
        }
    }


    @Override
    public boolean supports(@Nonnull DocumentationType documentationType) {
        return true;
    }
}
