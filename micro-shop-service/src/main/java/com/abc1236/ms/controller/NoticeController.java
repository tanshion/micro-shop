package com.abc1236.ms.controller;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.system.Notice;
import com.abc1236.ms.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultEntity<List<Notice>> list(String condition) {
        List<Notice> list = noticeService.getNoticeList(condition);
        return ResultEntity.success(list);
    }
}
