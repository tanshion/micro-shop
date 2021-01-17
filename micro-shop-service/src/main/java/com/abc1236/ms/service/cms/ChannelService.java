package com.abc1236.ms.service.cms;

import com.abc1236.ms.entity.cms.Channel;
import com.abc1236.ms.query.ChannelQuery;

import java.util.List;

public interface ChannelService {
    boolean insert(ChannelQuery channelQuery);

    boolean update(ChannelQuery channelQuery);

    boolean deleteById(Long id);

    List<Channel> queryAll();
}
