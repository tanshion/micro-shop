package com.abc1236.ms.service.task;

import com.abc1236.ms.util.JsonUtils;
import com.abc1236.ms.vo.QuartzJob;
import com.fasterxml.jackson.core.type.TypeReference;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        //QuartzJob job = (QuartzJob) data.get("job");
        String job = (String) data.get("job");
        try {
            QuartzJob quartzJob = JsonUtils.from(job, new TypeReference<QuartzJob>() {});
            Assert.notNull(quartzJob, "quartzJob为空");
            TaskUtils.executeJob(quartzJob);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
