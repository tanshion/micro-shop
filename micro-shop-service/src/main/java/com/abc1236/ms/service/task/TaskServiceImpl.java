package com.abc1236.ms.service.task;


import com.abc1236.ms.config.mybatis.SqlWrapper;
import com.abc1236.ms.entity.system.Task;
import com.abc1236.ms.exception.MyAssert;
import com.abc1236.ms.exception.ServiceException;
import com.abc1236.ms.mapper.system.TaskMapper;
import com.abc1236.ms.service.system.LogObjectHolder;
import com.abc1236.ms.vo.QuartzJob;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
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
    public boolean save(Task task) {
        log.info("新增定时任务:{}", task.getName());
        boolean success = SqlHelper.retBool(taskMapper.insert(task));
        try {
            jobService.addJob(jobService.getJob(task));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
        return success;
    }

    @Override
    public boolean updateById(Task task) {
        log.info("更新定时任务:{}", task.getName());
        MyAssert.notNull(taskMapper.selectById(task.getId()),"数据不存在");
        boolean success = SqlHelper.retBool(taskMapper.updateById(task));
        try {
            QuartzJob job = jobService.getJob(task.getId().toString(), task.getJobGroup());
            if (job != null) {
                jobService.deleteJob(job);
            }
            jobService.addJob(jobService.getJob(task));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
        return success;
    }

    @Override
    public boolean disable(Long taskId) {
        Task task = taskMapper.selectById(taskId);
        MyAssert.notNull(taskMapper.selectById(task.getId()),"数据不存在");
        boolean success = SqlWrapper.update(taskMapper)
            .eq(Task::getId, task)
            .set(Task::getDisabled, true)
            .update();
        log.info("禁用定时任务:{}", taskId);
        try {
            QuartzJob job = jobService.getJob(task.getId().toString(), task.getJobGroup());
            if (job != null) {
                jobService.deleteJob(job);
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
        return success;
    }

    @Override
    public boolean enable(Long taskId) {
        Task task = taskMapper.selectById(taskId);
        MyAssert.notNull(taskMapper.selectById(task.getId()),"数据不存在");
        boolean success = SqlWrapper.update(taskMapper)
            .eq(Task::getId, task)
            .set(Task::getDisabled, false)
            .update();
        log.info("启用定时任务{}", taskId);
        try {
            QuartzJob job = jobService.getJob(task.getId().toString(), task.getJobGroup());
            if (job != null) {
                jobService.deleteJob(job);
            }
            if (!task.getDisabled()) {
                jobService.addJob(jobService.getJob(task));
            }
        } catch (SchedulerException e) {
            throw  new ServiceException("定时任务配置错误");
        }
        return success;
    }

    @Override
    public boolean deleteById(Long id) {
        Task task = taskMapper.selectById(id);
        MyAssert.notNull(taskMapper.selectById(task.getId()),"数据不存在");
        task.setDisabled(true);
        boolean success = SqlHelper.retBool(taskMapper.deleteById(id));
        try {
            log.info("删除定时任务{}", id.toString());
            QuartzJob job = jobService.getJob(task);
            if (job != null) {
                jobService.deleteJob(job);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return success;
    }

    @Override
    public List<Task> queryAllByNameLike(String name) {
        return SqlWrapper.query(taskMapper)
            .like(Task::getName, "%" + name + "%")
            .list();
    }

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
    public List<Task> queryAll(boolean disabled) {
        return SqlWrapper.query(taskMapper)
            .eq(Task::getDisabled, disabled)
            .list();
    }

    @Override
    public List<Task> queryAll() {
        return SqlWrapper.query(taskMapper).list();
    }
}
