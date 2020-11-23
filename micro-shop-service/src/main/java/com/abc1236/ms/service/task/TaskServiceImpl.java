package com.abc1236.ms.service.task;


import com.abc1236.ms.config.mybatis.DaoWrapper;
import com.abc1236.ms.entity.system.Task;
import com.abc1236.ms.exception.MyAssert;
import com.abc1236.ms.mapper.system.TaskMapper;
import com.abc1236.ms.service.system.LogObjectHolder;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(Task task) {
        if (null != task.getId()) {
            MyAssert.notNull(taskMapper.selectById(task.getId()), "数据不存在");
            return SqlHelper.retBool(taskMapper.updateById(task));
        } else {
            return SqlHelper.retBool(taskMapper.insert(task));
        }
    }

    @Override
    public Task getById(Long id) {
        return taskMapper.selectById(id);
    }


    @Override
    public boolean save(Task task) {
        return SqlHelper.retBool(taskMapper.insert(task));
    }

    @Override
    public List<Task> queryAll(boolean disabled) {
        return DaoWrapper.query(taskMapper)
            .eq(Task::getDisabled, disabled)
            .list();
    }

    @Override
    public List<Task> queryAll() {
        return DaoWrapper.query(taskMapper).list();
    }

    @Override
    public List<Task> queryAllByNameLike(String name) {
        return DaoWrapper.query(taskMapper)
            .like(Task::getName, "%" + name + "%")
            .list();
    }

    @Override
    public void addTask(Task task) {
        if(task.getId()==null) {
            save(task);
        }else{
            Task old = getById(task.getId());
            LogObjectHolder.me().set(old);
            old.setName(task.getName());
            old.setCron(task.getCron());
            old.setJobClass(task.getJobClass());
            old.setNote(task.getNote());
            old.setData(task.getData());
            updateById(old);
        }
    }

    @Override
    public boolean updateById(Task task) {
        MyAssert.notNull(taskMapper.selectById(task.getId()),"数据不存在");
        return SqlHelper.retBool(taskMapper.updateById(task));
    }

    @Override
    public boolean deleteById(Long id) {
        return SqlHelper.retBool(taskMapper.deleteById(id));
    }

    @Override
    public boolean disable(Long taskId) {
        Task task = taskMapper.selectById(taskId);
        MyAssert.notNull(taskMapper.selectById(task.getId()),"数据不存在");
        return DaoWrapper.update(taskMapper)
            .eq(Task::getId, task)
            .set(Task::getDisabled, true)
            .update();
    }

    @Override
    public boolean enable(Long taskId) {
        Task task = taskMapper.selectById(taskId);
        MyAssert.notNull(taskMapper.selectById(task.getId()),"数据不存在");
        return DaoWrapper.update(taskMapper)
            .eq(Task::getId, task)
            .set(Task::getDisabled, false)
            .update();
    }


}
