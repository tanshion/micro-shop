package com.abc1236.ms.service;

import com.abc1236.ms.entity.system.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> getNoticeList(String condition);
}
