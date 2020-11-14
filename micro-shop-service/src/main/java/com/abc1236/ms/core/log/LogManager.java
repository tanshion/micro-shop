package com.abc1236.ms.core.log;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 日志管理器
 *
 * @author fengshuonan
 * @date 2017-03-30 16:29
 */
public class LogManager {

    /**
     * 异步操作记录日志的线程池
     */
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    private LogManager() {
    }

    public static LogManager logManager = new LogManager();

    public static LogManager me() {
        return logManager;
    }

    public void executeLog(TimerTask task) {
        //日志记录操作延时
        int OPERATE_DELAY_TIME = 10;
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
