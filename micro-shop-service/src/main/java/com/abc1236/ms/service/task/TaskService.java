package com.abc1236.ms.service.task;

import com.abc1236.ms.entity.system.Task;

import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);

    void save(Task task);

    List<Task> queryAll(boolean disabled);
}
