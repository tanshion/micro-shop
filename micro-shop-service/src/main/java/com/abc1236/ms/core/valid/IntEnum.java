package com.abc1236.ms.core.valid;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IntEnum.IntEnumValidatorInner.class})
public @interface IntEnum {

    /**
     * 必须的属性
     * 显示 校验信息
     * 利用 {} 获取 属性值，参考了官方的message编写方式
     *
     * @see org.hibernate.validator 静态资源包里面 message 编写方式
     */
    String message() default "int枚举值不正确";

    /**
     * 必须的属性
     * 用于分组校验
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 非必须
     */
    int[] intEmum();


    /**
     * 必须实现 ConstraintValidator接口
     */
    class IntEnumValidatorInner implements ConstraintValidator<IntEnum, Object> {
        private int[] intEmum;

        @Override
        public void initialize(IntEnum constraintAnnotation) {
            this.intEmum = constraintAnnotation.intEmum();

        }

        /**
         * 校验逻辑的实现
         *
         * @param value 需要校验的 值
         * @return 布尔值结果
         */
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            if (value instanceof Integer) {
                return Arrays.binarySearch(intEmum, (Integer) value) >= 0;
            }

            return false;
        }

    }
}
