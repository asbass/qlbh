package com.buoi2.ltw.service;

import com.buoi2.ltw.dao.AccountDAO;
import com.buoi2.ltw.dao.CategoryDAO;
import com.buoi2.ltw.dao.OrderDAO;
import com.buoi2.ltw.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
