package com.buoi2.ltw.controller;

import com.buoi2.ltw.entity.Order;
import com.buoi2.ltw.entity.OrderDetail;
import com.buoi2.ltw.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order-detail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/list-by-order/{orderId}")
    public ResponseEntity<List<OrderDetail>> getListByOrder(@PathVariable long orderId) {
        return orderDetailService.listAllOrderDetail(orderId);
    }
}
