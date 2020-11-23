package com.abc1236.ms.service.system.impl;

import com.abc1236.ms.entity.system.Cfg;
import com.abc1236.ms.mapper.system.CfgMapper;
import com.abc1236.ms.service.system.CfgService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CfgServiceImpl implements CfgService {

    private final CfgMapper cfgMapper;

    @Override
    public boolean updateById(Cfg cfg) {
        return SqlHelper.retBool(cfgMapper.updateById(cfg));
    }

    @Override
    public Cfg getById(long id) {
        return cfgMapper.selectById(id);
    }

}
