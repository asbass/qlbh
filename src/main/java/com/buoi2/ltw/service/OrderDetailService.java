package com.buoi2.ltw.service;

import com.buoi2.ltw.entity.OrderDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderDetailService {
    ResponseEntity<List<OrderDetail>> listAllOrderDetail(long orderId);

}
