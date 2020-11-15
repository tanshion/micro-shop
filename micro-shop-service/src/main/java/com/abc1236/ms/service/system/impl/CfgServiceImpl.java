package com.abc1236.ms.service.system.impl;

import com.abc1236.ms.dao.mapper.system.CfgMapper;
import com.abc1236.ms.entity.system.Cfg;
import com.abc1236.ms.service.system.CfgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CfgServiceImpl implements CfgService {

    private final CfgMapper cfgMapper;

    @Override
    public Cfg getCgfById(long id) {
        return cfgMapper.selectById(id);
    }

    @Override
    public void update(Cfg cfg) {
        cfgMapper.updateById(cfg);
    }
}
