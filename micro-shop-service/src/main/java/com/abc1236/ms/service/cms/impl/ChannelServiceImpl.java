package com.abc1236.ms.service.cms.impl;


import com.abc1236.ms.entity.cms.Channel;
import com.abc1236.ms.exception.MyAssert;
import com.abc1236.ms.mapper.cms.ChannelMapper;
import com.abc1236.ms.query.ChannelQuery;
import com.abc1236.ms.service.cms.ChannelService;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService {
    private final ChannelMapper channelMapper;

    @Override
    public boolean insert(ChannelQuery channelQuery) {
        log.info("新增栏目");
        Channel channel = BeanCopyUtils.copyBean(channelQuery, Channel.class);
        return Db.save(channel);
    }

    @Override
    public boolean update(ChannelQuery channelQuery) {
        log.info("更新栏目");
        Channel channel = BeanCopyUtils.copyBean(channelQuery, Channel.class);
        return SqlHelper.retBool(channelMapper.updateById(channel));
    }

    @Override
    public boolean deleteById(Long id) {
        log.info("删除栏目");
        Channel channel = channelMapper.selectById(id);
        MyAssert.notNull(channel, "栏目不存在");
        return SqlHelper.retBool(channelMapper.deleteById(id));
    }

    @Override
    public List<Channel> queryAll() {
        log.info("查询所有栏目");
        return Db.lambdaQuery(Channel.class).list();
    }
}
