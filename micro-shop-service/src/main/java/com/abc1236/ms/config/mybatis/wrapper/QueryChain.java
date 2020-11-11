package com.abc1236.ms.config.mybatis.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.ChainQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@SuppressWarnings({"serial"})
public class QueryChain<T> extends AbstractChainWrapper<T, SFunction<T, ?>, QueryChain<T>, LambdaQueryWrapper<T>>
    implements ChainQuery<T>, Query<QueryChain<T>, T, SFunction<T, ?>> {
    protected Log log = LogFactory.getLog(this.getClass());

    private final BaseMapper<T> baseMapper;

    public QueryChain(BaseMapper<T> baseMapper) {
        super();
        this.baseMapper = baseMapper;
        super.wrapperChildren = new LambdaQueryWrapper<>();
    }

    @SafeVarargs
    public final QueryChain<T> select(SFunction<T, ?>... columns) {
        wrapperChildren.select(columns);
        return typedThis;
    }

    public QueryChain<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(entityClass, predicate);
        return typedThis;
    }


    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    @Override
    public BaseMapper<T> getBaseMapper() {
        return baseMapper;
    }

    public T one(boolean throwEx) {
        if (throwEx) {
            one();
        }
        Page<T> page = page(new Page<>(1, 2));
        if (page == null || CollectionUtils.isEmpty(page.getRecords())) {
            return null;
        }
        return SqlHelper.getObject(this.log, page.getRecords());
    }

    public Optional<T> oneOpt(boolean throwEx) {
        if (throwEx) {
            oneOpt();
        }
        return Optional.ofNullable(this.one(false));
    }
}
