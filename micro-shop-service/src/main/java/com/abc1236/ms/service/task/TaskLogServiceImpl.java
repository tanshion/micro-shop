package com.abc1236.ms.service.task;


import com.abc1236.ms.entity.system.TaskLog;
import com.abc1236.ms.mapper.system.TaskLogMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskLogServiceImpl implements TaskLogService {
    private final TaskLogMapper taskLogMapper;

    @Override
    public boolean save(TaskLog entity) {
        return SqlHelper.retBool(taskLogMapper.insert(entity));
    }

    @Override
    public Page<TaskLog> queryPage(Long taskId, Page<TaskLog> page) {
        return Db.lambdaQuery(TaskLog.class)
            .eq(TaskLog::getId, taskId)
            .page(page);
    }
}
