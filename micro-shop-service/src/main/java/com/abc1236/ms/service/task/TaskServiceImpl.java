package com.abc1236.ms.service.task;


import com.abc1236.ms.config.mybatis.wrapper.QueryChain;
import com.abc1236.ms.dao.mapper.TaskMapper;
import com.abc1236.ms.entity.system.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tanshion
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    @Lazy
    @Resource
    private JobService jobService;


    public Task getTaskById(Long id) {
        return taskMapper.selectById(id);
    }

    @Override
    public void save(Task task) {
        if (task.getId() == null) {
            taskMapper.insert(task);
        } else {
            taskMapper.updateById(task);
        }
    }

    @Override
    public List<Task> queryAll(boolean disabled) {
        return new QueryChain<>(taskMapper)
            .eq(Task::isDisabled, false)
            .list();
    }
}
