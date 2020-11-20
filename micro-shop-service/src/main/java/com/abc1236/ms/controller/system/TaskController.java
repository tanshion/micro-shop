package com.abc1236.ms.controller.system;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.aop.BussinessLog;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.system.Task;
import com.abc1236.ms.entity.system.TaskLog;
import com.abc1236.ms.service.task.TaskLogService;
import com.abc1236.ms.service.task.TaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author tanshion
 */
@Api(tags = "定时任务")
@RequiredArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskLogService taskLogService;

    @ApiOperation("获取定时任务管理列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('" + Permission.TASK + "')")
    public ResultEntity<List<Task>> list(String name) {
        if (StrUtil.isBlank(name)) {
            return ResultEntity.success(taskService.queryAll());
        } else {
            return ResultEntity.success(taskService.queryAllByNameLike(name));
        }
    }

    @ApiOperation("新增定时任务管理")
    @PostMapping
    @BussinessLog(value = "编辑定时任务", key = "name")
    @PreAuthorize("hasAuthority('" + Permission.TASK_EDIT + "')")
    public ResultEntity<Object> add(@ModelAttribute @Valid Task task) {
        taskService.addTask(task);
        return ResultEntity.success();
    }

    @ApiOperation("删除定时任务管理")
    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除定时任务", key = "id")
    @PreAuthorize("hasAuthority('" + Permission.TASK_DEL + "')")
    public ResultEntity<Object> delete(@RequestParam Long id) {
        taskService.deleteById(id);
        return ResultEntity.success();
    }

    @ApiOperation("禁用定时任务")
    @PostMapping(value = "/disable")
    @BussinessLog(value = "禁用定时任务", key = "taskId")
    @PreAuthorize("hasAuthority('" + Permission.TASK_EDIT + "')")
    public ResultEntity<Object> disable(@RequestParam Long taskId) {
        taskService.disable(taskId);
        return ResultEntity.success();
    }

    @ApiOperation("启用定时任务")
    @PostMapping(value = "/enable")
    @BussinessLog(value = "启用定时任务", key = "taskId")
    @PreAuthorize("hasAuthority('" + Permission.TASK_EDIT + "')")
    public ResultEntity<Object> enable(@RequestParam Long taskId) {
        taskService.enable(taskId);
        return ResultEntity.success();
    }

    @ApiOperation("定时任务日志")
    @GetMapping(value = "/logList")
    @PreAuthorize("hasAuthority('" + Permission.TASK + "')")
    public ResultEntity<Page<TaskLog>> logList(@RequestParam Long taskId,
        @RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "20") Long limit) {
        Page<TaskLog> iPage = taskLogService.queryPage(taskId,new Page<>(page,limit));
        return ResultEntity.success(iPage);
    }

}
