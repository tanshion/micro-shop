package com.abc1236.ms.service.task.job;

import com.abc1236.ms.entity.system.Cfg;
import com.abc1236.ms.service.system.CfgService;
import com.abc1236.ms.service.task.JobExecuter;
import com.abc1236.ms.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author tanshoin
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class HelloJob extends JobExecuter {

    private final CfgService cfgService;

    @Override
    public void execute(Map<String, Object> dataMap) {
        Cfg cfg = cfgService.getCgfById(1L);
        cfg.setCfgDesc("应用名称");
        cfgService.update(cfg);
        log.info("hello :" + JsonUtils.to(dataMap));
    }
}
