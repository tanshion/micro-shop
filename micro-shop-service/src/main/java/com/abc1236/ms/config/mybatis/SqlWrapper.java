package com.abc1236.ms.config.mybatis;

import com.abc1236.ms.config.mybatis.wrapper.QueryChain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;

/**
 * 快捷构造 chain 式调用的工具类
 *
 * @author miemie
 * @since 2019-11-28
 * @since 3.3.0
 */
public final class SqlWrapper {

    private SqlWrapper() {
        // ignore
    }

    /**
     * 链式查询 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @return LambdaQueryWrapper 的包装类
     */
    public static <T> QueryChain<T> query(BaseMapper<T> mapper) {
        return new QueryChain<>(mapper);
    }

    /**
     * 链式更改 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @return LambdaUpdateWrapper 的包装类
     */
    public static <T> LambdaUpdateChainWrapper<T> update(BaseMapper<T> mapper) {
        return new LambdaUpdateChainWrapper<>(mapper);
    }
}
