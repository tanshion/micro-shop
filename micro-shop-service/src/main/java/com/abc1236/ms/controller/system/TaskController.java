package com.abc1236.ms.controller.system;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.aop.BussinessLog;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.system.Task;
import com.abc1236.ms.service.task.TaskLogService;
import com.abc1236.ms.service.task.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author tanshion
 */
@Api(tags = "定时任务")
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskLogService taskLogService;


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
    public ResultEntity<String> add(@ModelAttribute @Valid Task task) {
        taskService.addTask(task);
        return ResultEntity.success();
    }

    ///**
    // * 删除定时任务管理
    // */
    //@RequestMapping(method = RequestMethod.DELETE)
    //
    //@BussinessLog(value = "删除定时任务", key = "id")
    //@RequiresPermissions(value = {Permission.TASK_DEL})
    //public Object delete(@RequestParam Long id) {
    //    taskService.deleteById(id);
    //    return Rets.success();
    //}
    //
    //@RequestMapping(value = "/disable",method = RequestMethod.POST)
    //
    //@BussinessLog(value = "禁用定时任务", key = "taskId")
    //@RequiresPermissions(value = {Permission.TASK_EDIT})
    //public Object disable(@RequestParam Long taskId  ) {
    //    taskService.disable(taskId);
    //    return Rets.success();
    //}
    //@RequestMapping(value = "/enable",method = RequestMethod.POST)
    //@BussinessLog(value = "启用定时任务", key = "taskId")
    //@RequiresPermissions(value = {Permission.TASK_EDIT})
    //public Object enable(@RequestParam Long taskId  ) {
    //    taskService.enable(taskId);
    //    return Rets.success();
    //}
    //
    //
    //@RequestMapping(value="/logList")
    //@RequiresPermissions(value = {Permission.TASK})
    //public Object logList(@RequestParam  Long taskId) {
    //    Page<TaskLog> page = new PageFactory<TaskLog>().defaultPage();
    //    page.addFilter(SearchFilter.build("idTask", SearchFilter.Operator.EQ,taskId));
    //    page = taskLogService.queryPage(page);
    //    return Rets.success(page);
    //}

}
