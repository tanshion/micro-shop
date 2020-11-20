package com.abc1236.ms.service.task;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.result.ResultStatus;
import com.abc1236.ms.entity.system.Task;
import com.abc1236.ms.exception.ServiceException;
import com.abc1236.ms.util.JsonUtils;
import com.abc1236.ms.vo.QuartzJob;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final Scheduler scheduler;
    private final TaskService taskService;

    /**
     * 获取单个任务
     */
    @Override
    public QuartzJob getJob(String jobName, String jobGroup) throws SchedulerException {
        QuartzJob job = null;
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (null != trigger) {
            job = new QuartzJob();
            job.setJobName(jobName);
            job.setJobGroup(jobGroup);
            job.setDescription("触发器:" + trigger.getKey());
            job.setNextTime(trigger.getNextFireTime());
            job.setPreviousTime(trigger.getPreviousFireTime());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
        }
        return job;
    }

    @Override
    public List<QuartzJob> getTaskList() {
        List<Task> tasks = taskService.queryAll(false);
        List<QuartzJob> jobs = new ArrayList<>();
        for (Task task : tasks) {
            jobs.add(getJob(task));
        }
        return jobs;
    }

    @Override
    public QuartzJob getJob(Task task) {
        QuartzJob job = null;
        if (task != null) {
            job = new QuartzJob();
            job.setJobName(String.valueOf(task.getId()));
            job.setJobGroup(task.getJobGroup());
            job.setCronExpression(task.getCron());
            job.setConcurrent(task.getConcurrent());
            job.setJobClass(task.getJobClass());
            job.setDescription(task.getName());
            job.setDisabled(task.getDisabled());
            if (StrUtil.isNotEmpty(task.getData())) {
                try {
                    Map<String, Object> dataMap = JsonUtils.from(task.getData(), new TypeReference<Map<String, Object>>() {});
                    job.setDataMap(dataMap);
                } catch (Exception e) {
                    throw new ServiceException(ResultStatus.TASK_CONFIG_ERROR);
                }
            }
        }
        return job;
    }

    /**
     * 添加任务
     */
    @Override
    public boolean addJob(QuartzJob job) throws SchedulerException {
        log.info("新增任务Id：{}, name:{}", job.getJobName(), job.getDescription());
        if (job.isDisabled()) {
            return false;
        }
        if (!TaskUtils.isValidExpression(job.getCronExpression())) {
            log.error("时间表达式错误（" + job.getJobName() + "," + job.getJobGroup() + "）," + job.getCronExpression());
            return false;
        } else {
            // 任务名称和任务组设置规则：    // 名称：task_1 ..    // 组 ：group_1 ..
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在，创建一个
            if (null == trigger) {
                //是否允许并发执行
                Class<? extends Job> clazz = job.isConcurrent() ? BaseJob.class : NoConurrentBaseJob.class;
                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put("job", JsonUtils.to(job));
                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                // 按新的表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {     // trigger已存在，则更新相应的定时设置
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        }
        return true;
    }

    /**
     * 删除任务
     */
    @Override
    public boolean deleteJob(QuartzJob record) {
        log.info("删除任务：{}", record.getJobName());
        JobKey jobKey = JobKey.jobKey(record.getJobName(), record.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

}
