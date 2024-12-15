package com.buoi2.ltw.controller;

import com.buoi2.ltw.dto.CreateOrderDTO;
import com.buoi2.ltw.dto.UpdateStatusOrderDTO;
import com.buoi2.ltw.entity.Account;
import com.buoi2.ltw.entity.Order;
import com.buoi2.ltw.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create")
    public String createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO) {
        return orderService.addOrder(createOrderDTO);
    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable long orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @PutMapping("/update-status/{orderId}")
    public String updateStatusOrder(@PathVariable long orderId, @Valid @RequestBody UpdateStatusOrderDTO updateStatusOrderDTO) {
        return orderService.updateStatusOrder(orderId, updateStatusOrderDTO);
    }
}
