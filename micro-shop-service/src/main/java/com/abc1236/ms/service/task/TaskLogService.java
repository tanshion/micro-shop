package com.abc1236.ms.service.task;


import com.abc1236.ms.entity.system.TaskLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 定时任务日志服务类
 *
 * @author tanshion
 */
public interface TaskLogService {
    boolean save(TaskLog entity);

    Page<TaskLog> queryPage(Long taskId, Page<TaskLog> page);
}
