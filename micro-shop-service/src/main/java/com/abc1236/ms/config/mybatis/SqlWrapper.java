package com.abc1236.ms.config.mybatis;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import java.time.LocalDateTime;
import java.util.List;

public class SqlWrapper {

    public static <T> void range(LambdaQueryWrapper<T> wrappers, SFunction<T, ?> column, List<LocalDateTime> dateTimes) {
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if (null != dateTimes) {
            for (int i = 0; i < dateTimes.size(); ++i) {
                if (i == 0) {
                    startDateTime = LocalDateTimeUtil.beginOfDay(dateTimes.get(0));
                } else if (i == 1) {
                    endDateTime = LocalDateTimeUtil.endOfDay(dateTimes.get(1));
                }
            }
        }
        wrappers.ge(null != startDateTime, column, startDateTime)
            .le(null != endDateTime, column, endDateTime);
    }


    public static <T> void range(LambdaQueryChainWrapper<T> wrappers, SFunction<T, ?> column, List<LocalDateTime> dateTimes) {
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if (null != dateTimes) {
            for (int i = 0; i < dateTimes.size(); ++i) {
                if (i == 0) {
                    startDateTime = LocalDateTimeUtil.beginOfDay(dateTimes.get(0));
                } else if (i == 1) {
                    endDateTime = LocalDateTimeUtil.endOfDay(dateTimes.get(1));
                }
            }
        }
        wrappers.ge(null != startDateTime, column, startDateTime)
            .le(null != endDateTime, column, endDateTime);
    }

}
