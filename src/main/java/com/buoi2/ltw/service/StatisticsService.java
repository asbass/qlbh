package com.buoi2.ltw.service;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    long getTotalOrders();
    long getTotalProducts();
    long getTotalUsers();
    List<Map<String, Object>> getOrderCountByMonth(int year);
    List<Map<String, Object>> getOrderStatusCount();  // Thống kê tình trạng đơn hàng
    List<Map<String, Object>> getTopBuyersByMonth(int year, int month);
}
