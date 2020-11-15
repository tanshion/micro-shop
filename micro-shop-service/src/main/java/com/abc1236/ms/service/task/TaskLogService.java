package com.abc1236.ms.service.task;


import com.abc1236.ms.entity.system.TaskLog;

/**
 * 定时任务日志服务类
 *
 * @author tanshion
 */
public interface TaskLogService {
    void save(TaskLog taskLog);
}
