package com.abc1236.ms.controller.cms;


import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.aop.BussinessLog;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Banner;
import com.abc1236.ms.exception.ServiceException;
import com.abc1236.ms.query.BannerQuery;
import com.abc1236.ms.service.cms.BannerService;
import com.abc1236.ms.util.JsonUtils;
import com.abc1236.ms.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "banner管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/banner")
public class BannerMgrController {
    private final BannerService bannerService;

    @ApiOperation("编辑banner")
    @PostMapping
    @BussinessLog(value = "编辑banner", key = "title")
    @PreAuthorize("hasAuthority('" + Permission.BANNER_EDIT + "')")
    public ResultEntity<Object> save(@ModelAttribute @Valid BannerQuery bannerQuery) {
        if (StringUtil.isNotEmpty(bannerQuery.getParam())) {
            if (!JsonUtils.isJson(bannerQuery.getParam())) {
                throw new ServiceException("参数必须为json格式");
            }
        }
        bannerService.save(bannerQuery);
        return ResultEntity.success();
    }

    @ApiOperation("删除banner")
    @DeleteMapping
    @BussinessLog(value = "删除banner", key = "id")
    @PreAuthorize("hasAuthority('" + Permission.BANNER_DEL + "')")
    public ResultEntity<Object> remove(Long id) {
        bannerService.remove(id);
        return ResultEntity.success();
    }

    @ApiOperation("搜索BANNER")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('" + Permission.BANNER + "')")
    public ResultEntity<List<Banner>> list(@RequestParam(required = false) String title) {
        List<Banner> list = bannerService.queryAllLike(title);
        return ResultEntity.success(list);
    }
}
