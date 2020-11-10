package com.abc1236.ms.config.swagger;


import com.abc1236.ms.core.authentication.constant.TokenConstant;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * swagger配置
 *
 * @author tanshion
 * @email 843565561@qq.com
 */
@Configuration
public class SwaggerConfig {

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**", "/oauth/**");
    private static final String BASE_PATH = "/**";


    @Primary
    @Bean
    public EnumValuePropertyPlugin enumValuePropertyPlugin() {
        return new EnumValuePropertyPlugin();
    }

    @Primary
    @Bean
    public EnumValueParameterBuilderPlugin enumValueParameterBuilderPlugin() {
        return new EnumValueParameterBuilderPlugin();
    }

    @Bean
    public Docket api() {
        Predicate<String> basePath = PathSelectors.ant(BASE_PATH);
        List<Predicate<String>> excludePath = new ArrayList<>();
        DEFAULT_EXCLUDE_PATH.forEach(path -> excludePath.add(PathSelectors.ant(path)));
        Predicate<String> selector = basePath.and(new OrPredicate<>(excludePath).negate());

        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .paths(selector)
            .build()
            .securitySchemes(securitySchema())
            .securityContexts(securityContext())
            .pathMapping("/");
    }

    private static class OrPredicate<T> implements Predicate<T>, Serializable {
        private final List<? extends Predicate<? super T>> components;

        private OrPredicate(List<? extends Predicate<? super T>> components) {
            this.components = components;
        }

        @Override
        public boolean test(@Nullable T t) {
            for (Predicate<? super T> component : components) {
                if (component.test(t)) {
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * 通过Swagger2的securitySchemes配置全局参数：如下列代码所示，
     * securitySchemes的ApiKey中增加一个名为“Access-Token”，type为“header”的参数。
     */
    private List<SecurityScheme> securitySchema() {
        return Collections.singletonList(
            new ApiKey(TokenConstant.HEADER_NAME_TOKEN, TokenConstant.HEADER_NAME_TOKEN, "header"));
    }


    /**
     * 在Swagger2的securityContexts中通过正则表达式，设置需要使用参数的接口（或者说，是去除掉不需要使用参数的接口），如下列代码所示，
     * 通过PathSelectors.regex("^(?!auth).*$")，所有包含"auth"的接口不需要使用securitySchemes。
     * 即不需要使用上文中设置的名为“Access-Token”，type为“header”的参数。
     */
    private List<SecurityContext> securityContext() {
        return Collections.singletonList(
            SecurityContext.builder()
                .securityReferences(defaultAuth())
                //.operationSelector(operationContext -> operationContext.requestMappingPattern().matches("^(?!auth).*$"))
                //.operationSelector(operationContext -> operationContext.requestMappingPattern().contains("abc"))
                .build()
        );
    }

    /**
     * 默认的全局鉴权策略
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(TokenConstant.HEADER_NAME_TOKEN, authorizationScopes));
    }

}
