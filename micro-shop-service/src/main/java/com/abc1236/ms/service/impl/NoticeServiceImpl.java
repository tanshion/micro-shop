package com.abc1236.ms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.config.mybatis.wrapper.QueryChain;
import com.abc1236.ms.entity.system.Notice;
import com.abc1236.ms.mapper.system.NoticeMapper;
import com.abc1236.ms.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeMapper noticeMapper;

    @Override
    public List<Notice> getNoticeList(String condition) {
        List<Notice> list;
        if (StrUtil.isBlank(condition)) {
            list = new QueryChain<>(noticeMapper)
                .list();
        } else {
            list = new QueryChain<>(noticeMapper)
                .like(Notice::getTitle, "%" + condition + "%")
                .list();
        }
        return list;
    }
}
