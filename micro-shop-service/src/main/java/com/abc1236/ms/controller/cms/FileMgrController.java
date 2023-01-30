package com.abc1236.ms.controller.cms;

import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.system.FileInfo;
import com.abc1236.ms.service.system.FileService;
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

@Api(tags = "文件管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/fileMgr")
public class FileMgrController {
    private final FileService fileService;

    @ApiOperation("文件列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('" + Permission.FILE + "')")
    public ResultEntity<Page<FileInfo>> list(Long page, Long limit,
        @RequestParam(required = false) String originalFileName

    ) {
        Page<FileInfo> res = fileService.queryPageByLike(page, limit, originalFileName);
        return ResultEntity.success(res);
    }
}
