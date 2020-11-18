package com.abc1236.ms.service.task;

import com.abc1236.ms.dao.mapper.system.TaskLogMapper;
import com.abc1236.ms.entity.system.TaskLog;
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
}
