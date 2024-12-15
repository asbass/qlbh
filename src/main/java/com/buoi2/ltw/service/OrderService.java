package com.buoi2.ltw.service;

import com.buoi2.ltw.dto.CreateOrderDTO;
import com.buoi2.ltw.dto.UpdateStatusOrderDTO;
import com.buoi2.ltw.entity.Order;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface OrderService {
    ResponseEntity<List<Order>> listAllOrder();

    ResponseEntity<Order> getOrderDetails(Long orderId);

    ResponseEntity<Order> addOrder(CreateOrderDTO createOrderDTO) throws Exception;

    ResponseEntity<Order> updateStatusOrder(Long orderId, UpdateStatusOrderDTO updateStatusOrderDTO) throws Exception;

}
