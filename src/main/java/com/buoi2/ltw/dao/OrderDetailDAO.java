package com.buoi2.ltw.dao;

import java.util.List;

import com.buoi2.ltw.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


//@Repository
//public interface OrderDetailDAO extends JpaRepository<OrderDAO, Long>{
//	@Query("SELECT new Report(o.id, sum(o.price), count(o)) "
//			+ " FROM OrderDetail o "
//			+ " GROUP BY o.id"
//			+ " ORDER BY sum(o.price) DESC")
//	List<Report> getInventoryByoder();
////	List<OrderDetail> findByOrder();
//
//
//}