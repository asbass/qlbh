package com.buoi2.ltw.controller;

import com.buoi2.ltw.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public Map<String, Long> getStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalOrders", statisticsService.getTotalOrders());
        stats.put("TotalProducts", statisticsService.getTotalProducts());
        stats.put("totalUsers", statisticsService.getTotalUsers());
        return stats;
    }
}
