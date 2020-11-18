package com.abc1236.ms.service.task;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.constant.DBFieldConstant;
import com.abc1236.ms.entity.system.Task;
import com.abc1236.ms.entity.system.TaskLog;
import com.abc1236.ms.vo.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author tanshion
 */
@Slf4j
@Component
public abstract class JobExecuter {

    @Resource
    private TaskService taskService;
    @Resource
    private TaskLogService taskLogService;

    private QuartzJob job;

    public void setJob(QuartzJob job) {
        this.job = job;
    }

    public void execute() {
        Map<String, Object> dataMap = job.getDataMap();
        String taskId = job.getJobName();
        Task task = taskService.getById(Long.valueOf(taskId));
        final String taskName = task.getName();
        log.info(">>>>>>>>>>>>>>>>>开始执行定时任务[" + taskName + "]...<<<<<<<<<<<<<<<<<<<");

        String exeResult = "执行成功";
        final TaskLog taskLog = new TaskLog();
        taskLog.setName(taskName);
        final Date exeAt = new Date();
        taskLog.setExecAt(exeAt);
        taskLog.setIdTask(task.getId());
        //默认是成功 出异常后改成失败
        taskLog.setExecSuccess(DBFieldConstant.SYS_TASK_LOG_EXE_SUCCESS_RESULT);
        try {
            execute(dataMap);
            task.setExecResult(exeResult);
        } catch (Exception e) {
            task.setExecResult("执行失败");
            log.error("exeucte " + getClass().getName() + " error : ", e);
            exeResult = "执行失败\n";
            exeResult += ExceptionUtil.stacktraceToString(e);
            taskLog.setExecSuccess(DBFieldConstant.SYS_TASK_LOG_EXE_FAILURE_RESULT);
            taskLog.setJobException(exeResult);
        }

        task.setExecAt(exeAt);
        taskLogService.save(taskLog);
        taskService.saveOrUpdate(task);
        log.info(">>>>>>>>>>>>>>>>>执行定时任务[" + taskName + "]结束<<<<<<<<<<<<<<<<<<<");
    }

    /**
     * @param dataMap 数据库配置的参数
     */
    public abstract void execute(Map<String, Object> dataMap) throws Exception;

    public String getEmail() {
        return getEmail("snowalert@xuezhongdai.cn");
    }

    public String getEmail(String defaultEmail) {
        Map<String, Object> dataMap = job.getDataMap();
        String toEmail = null;
        if (dataMap != null && dataMap.containsKey("email")) {
            toEmail = Objects.toString(dataMap.get("email"), "");
        }
        if (StrUtil.isEmpty(toEmail)) {
            toEmail = defaultEmail;
        }
        return toEmail;
    }

}
