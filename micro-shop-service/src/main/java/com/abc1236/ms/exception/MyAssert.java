package com.abc1236.ms.exception;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
public abstract class MyAssert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(message);
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new ServiceException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }

    public static void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new ServiceException(message);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ServiceException(message);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new ServiceException(message);
        }
    }

}
