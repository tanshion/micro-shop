package com.abc1236.ms.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.entity.system.Notice;
import com.abc1236.ms.mapper.system.NoticeMapper;
import com.abc1236.ms.service.system.NoticeService;
import com.baomidou.mybatisplus.extension.toolkit.Db;
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
        return Db.lambdaQuery(Notice.class)
                .like(StrUtil.isBlank(condition), Notice::getTitle, "%" + condition + "%")
                .list();
    }
}
