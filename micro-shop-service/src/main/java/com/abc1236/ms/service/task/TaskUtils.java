package com.abc1236.ms.service.task;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.SpringContextHolder;
import com.abc1236.ms.vo.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.util.Date;

@Slf4j
public class TaskUtils {


    /**
     * 通过反射调用job中定义的方法
     */
    public static void executeJob(QuartzJob job) throws Exception {
        JobExecuter jobExecuter = null;
        Class<?> clazz;
        if (StrUtil.isNotEmpty(job.getJobClass())) {
            clazz = Class.forName(job.getJobClass());
            jobExecuter = (JobExecuter) SpringContextHolder.getBean(clazz);
            jobExecuter.setJob(job);
        }
        if (jobExecuter == null) {
            throw new RuntimeException("任务名称 = [" + job.getDescription() + "]未启动成功，请检查执行类是否配置正确！！！");
        }
        jobExecuter.execute();
    }

    /**
     * 判断cron时间表达式正确性
     */
    public static boolean isValidExpression(final String cronExpression) {
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

}
