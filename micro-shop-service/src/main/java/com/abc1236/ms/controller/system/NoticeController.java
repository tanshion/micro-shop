package com.abc1236.ms.controller.system;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.system.Notice;
import com.abc1236.ms.service.system.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "通知")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @ApiOperation("获取通知列表")
    @GetMapping(value = "/list")
    public ResultEntity<List<Notice>> list(String condition) {
        List<Notice> list = noticeService.getNoticeList(condition);
        return ResultEntity.success(list);
    }
}
