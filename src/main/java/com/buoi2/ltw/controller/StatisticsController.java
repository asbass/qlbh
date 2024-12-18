package com.buoi2.ltw.controller;

import com.buoi2.ltw.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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

    // API lấy thống kê số lượng đơn hàng theo tháng
    @GetMapping("/order-count-by-month")
    public List<Map<String, Object>> getOrderCountByMonth(@RequestParam int year) {
        return statisticsService.getOrderCountByMonth(year);
    }

    // API lấy thống kê tình trạng đơn hàng
    @GetMapping("/order-status-count")
    public List<Map<String, Object>> getOrderStatusCount() {
        return statisticsService.getOrderStatusCount();
    }

    // API lấy thống kê 10 người mua nhiều nhất theo tháng
    @GetMapping("/top-buyers")
    public List<Map<String, Object>> getTopBuyersByMonth(@RequestParam int year, @RequestParam int month) {
        List<Map<String, Object>> result = statisticsService.getTopBuyersByMonth(year, month);
        System.out.println("Result: " + result);  // Log dữ liệu
        return result;
    }

}
