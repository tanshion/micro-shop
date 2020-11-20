package com.abc1236.ms.service.task;

import com.abc1236.ms.entity.system.Task;

import java.util.List;

public interface TaskService {
    boolean save(Task task);

    List<Task> queryAll(boolean disabled);

    List<Task> queryAll();

    List<Task> queryAllByNameLike(String name);

    void addTask(Task task);

    boolean saveOrUpdate(Task task);

    Task getById(Long id);

    boolean updateById(Task task);

    boolean deleteById(Long id);

    boolean disable(Long taskId);

    boolean enable(Long taskId);
}
