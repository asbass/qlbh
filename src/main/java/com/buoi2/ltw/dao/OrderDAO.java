package com.buoi2.ltw.dao;

import com.buoi2.ltw.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {
    long count();
}
