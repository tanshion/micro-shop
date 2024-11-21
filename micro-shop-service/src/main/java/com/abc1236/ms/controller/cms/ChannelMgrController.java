package com.abc1236.ms.controller.cms;

import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.aop.BussinessLog;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Channel;
import com.abc1236.ms.query.ChannelQuery;
import com.abc1236.ms.service.cms.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "栏目管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/channel")
public class ChannelMgrController {
    private final ChannelService channelService;

    @ApiOperation("编辑栏目")
    @PostMapping
    @BussinessLog(value = "编辑栏目", key = "name")
    @PreAuthorize("hasAuthority('" + Permission.CHANNEL_EDIT + "')")
    public ResultEntity<String> save(@ModelAttribute @Valid ChannelQuery channelQuery) {
        if (channelQuery.getId() == null) {
            channelService.insert(channelQuery);
        } else {
            channelService.update(channelQuery);
        }
        return ResultEntity.success();
    }

    @ApiOperation("删除栏目")
    @DeleteMapping
    @BussinessLog(value = "删除栏目", key = "id")
    @PreAuthorize("hasAuthority('" + Permission.CHANNEL_DEL + "')")
    public ResultEntity<String> remove(Long id) {
        channelService.deleteById(id);
        return ResultEntity.success();
    }

    @ApiOperation("栏目列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Permission.CHANNEL + "')")
    public ResultEntity<List<Channel>> list() {
        List<Channel> list = channelService.queryAll();
        return ResultEntity.success(list);
    }
}
