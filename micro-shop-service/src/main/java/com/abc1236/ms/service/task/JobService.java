package com.abc1236.ms.service.task;

import com.abc1236.ms.entity.system.Task;
import com.abc1236.ms.vo.QuartzJob;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 任务服务
 *
 * @author tanshion
 */

interface JobService {

    /**
     * 获取单个任务
     */
    QuartzJob getJob(String jobName, String jobGroup) throws SchedulerException;

    List<QuartzJob> getTaskList();

    QuartzJob getJob(Task task);

    /**
     * 添加任务
     */
    boolean addJob(QuartzJob job) throws SchedulerException;

    /**
     * 删除任务
     */
    boolean deleteJob(QuartzJob record);


}
