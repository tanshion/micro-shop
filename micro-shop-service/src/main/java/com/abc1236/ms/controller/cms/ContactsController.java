package com.abc1236.ms.controller.cms;

import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Contacts;
import com.abc1236.ms.service.cms.ContactsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "邀约信息管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/contacts")
public class ContactsController {
    private final ContactsService contactsService;

    @ApiOperation("邀约信息列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('" + Permission.CONTACTS + "')")
    public Object list(Long page, Long limit,
        @RequestParam(required = false) String userName,
        @RequestParam(required = false) String mobile,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate

    ) {
        Page<Contacts> list = contactsService.queryPage(page, limit, userName, mobile, startDate, endDate);
        return ResultEntity.success(list);
    }
}