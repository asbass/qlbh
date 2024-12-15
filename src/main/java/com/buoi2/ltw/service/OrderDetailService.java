package com.buoi2.ltw.service;

import com.buoi2.ltw.dao.OrderDetailDAO;
import com.buoi2.ltw.entity.Order;
import com.buoi2.ltw.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    public ResponseEntity<List<OrderDetail>> listAllOrderDetail(long orderId) {
        List<OrderDetail> listOrderDetail = orderDetailDAO.findByOrderId(orderId);
        if (listOrderDetail.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<OrderDetail>>(listOrderDetail, HttpStatus.OK);
    }
}
