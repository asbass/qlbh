package com.buoi2.ltw.controller;

import com.buoi2.ltw.dto.CreateOrderDTO;
import com.buoi2.ltw.dto.UpdateStatusOrderDTO;
import com.buoi2.ltw.entity.Account;
import com.buoi2.ltw.entity.Order;
import com.buoi2.ltw.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<List<Order>> getList() {
        return orderService.listAllOrder();
    }

    @GetMapping("/list-by-account/{userName}")
    public ResponseEntity<List<Order>> getListByAccount(@PathVariable String userName) {
        return orderService.listAllOrderByUser(userName);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO) {
        try {
            return orderService.addOrder(createOrderDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable long orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<Order> updateStatusOrder(@PathVariable long orderId, @Valid @RequestBody UpdateStatusOrderDTO updateStatusOrderDTO) {
        try {
            return orderService.updateStatusOrder(orderId, updateStatusOrderDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
