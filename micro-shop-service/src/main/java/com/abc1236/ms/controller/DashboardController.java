package com.abc1236.ms.controller;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ：tanshion
 */
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@RestController
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public Object get() {
        Map data = dashboardService.getDashboardData();
        return ResultEntity.success(data);
    }
}
