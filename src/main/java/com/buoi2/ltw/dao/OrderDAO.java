package com.buoi2.ltw.dao;

import com.buoi2.ltw.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {
    long count();
    @Query("SELECT EXTRACT(MONTH FROM o.createDate) AS month, COUNT(o) AS orderCount " +
            "FROM Order o " +
            "WHERE EXTRACT(YEAR FROM o.createDate) = :year " +
            "GROUP BY month")
    List<Object[]> getOrderCountByMonth(@Param("year") int year);
    // Thống kê số lượng đơn hàng theo tháng
    @Query("SELECT o.status, COUNT(o) FROM Order o GROUP BY o.status")
    List<Object[]> getOrderStatusCount();
    @Query(value = "SELECT o.username, SUM(od.price * od.quantity) AS totalSpent " +
            "FROM Orders o " +
            "JOIN OrderDetails od ON o.id = od.orderid " +
            "WHERE YEAR(o.createDate) = :year AND MONTH(o.createDate) = :month " +
            "GROUP BY o.username " +
            "ORDER BY totalSpent DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getTopBuyersByMonth(@Param("year") int year, @Param("month") int month);
    @Query("SELECT o FROM Order o WHERE o.account.username = :username")
    List<Order> getOrdersByUsername(@Param("username") String username);
}
