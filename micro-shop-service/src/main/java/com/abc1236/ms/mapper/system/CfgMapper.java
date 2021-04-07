package com.abc1236.ms.mapper.system;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.system.Cfg;
import com.abc1236.ms.vo.UserTestDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CfgMapper extends BaseMapper<Cfg> {
    ResultEntity<List<UserTestDto>> getUserTestDto();
}