package com.buoi2.ltw.dao;

import java.util.List;

import com.buoi2.ltw.entity.OrderDetail;
import com.buoi2.ltw.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{
	public List<OrderDetail> findByOrderId(Long orderId);
}