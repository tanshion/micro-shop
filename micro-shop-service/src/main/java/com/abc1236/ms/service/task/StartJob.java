package com.abc1236.ms.service.task;

import com.abc1236.ms.vo.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动定时任务
 *
 * @author enilu
 * @date 2019-08-13
 */
@Slf4j
@Component
public class StartJob implements ApplicationRunner {

    @Autowired
    private JobService jobService;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("start Job >>>>>>>>>>>>>>>>>>>>>>>");
        List<QuartzJob> list = jobService.getTaskList();
        for (QuartzJob quartzJob : list) {
            jobService.addJob(quartzJob);
        }
    }
}