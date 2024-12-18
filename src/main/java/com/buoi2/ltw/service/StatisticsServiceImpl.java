package com.buoi2.ltw.service;

import com.buoi2.ltw.dao.AccountDAO;
import com.buoi2.ltw.dao.CategoryDAO;
import com.buoi2.ltw.dao.OrderDAO;
import com.buoi2.ltw.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderDAO orderRepository;
    private final ProductDAO productRepository;
    private final AccountDAO userRepository;

    @Autowired
    public StatisticsServiceImpl(OrderDAO orderRepository,
                                 ProductDAO productRepository,
                                 AccountDAO userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long getTotalOrders() {
        return orderRepository.count();
    }

    @Override
    public long getTotalProducts() {
        return productRepository.count();
    }

    @Override
    public long getTotalUsers() {
        return userRepository.count();
    }
    public List<Map<String, Object>> getOrderCountByMonth(int year) {
        List<Object[]> results = orderRepository.getOrderCountByMonth(year);
        List<Map<String, Object>> orderStats = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> stat = new HashMap<>();
            stat.put("month", result[0]);
            stat.put("orderCount", result[1]);
            orderStats.add(stat);
        }

        return orderStats;
    }

    // Thống kê tình trạng đơn hàng
    @Override
    public List<Map<String, Object>> getOrderStatusCount() {
        List<Object[]> results = orderRepository.getOrderStatusCount(); // Cần viết phương thức này trong OrderDAO
        List<Map<String, Object>> statusStats = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> stat = new HashMap<>();
            stat.put("status", result[0]);
            stat.put("count", result[1]);
            statusStats.add(stat);
        }

        return statusStats;
    }

    // Thống kê 10 người mua nhiều nhất theo tháng
    @Override
    public List<Map<String, Object>> getTopBuyersByMonth(int year, int month) {
        List<Object[]> results = orderRepository.getTopBuyersByMonth(year, month);
        List<Map<String, Object>> topBuyers = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> stat = new HashMap<>();
            stat.put("username", result[0]);
            stat.put("totalSpent", result[1]);
            topBuyers.add(stat);
        }
        return topBuyers;
    }
}
