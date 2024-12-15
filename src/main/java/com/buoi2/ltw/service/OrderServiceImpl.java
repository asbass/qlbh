package com.buoi2.ltw.service;

import com.buoi2.ltw.dao.AccountDAO;
import com.buoi2.ltw.dao.OrderDAO;
import com.buoi2.ltw.dao.OrderDetailDAO;
import com.buoi2.ltw.dao.ProductDAO;
import com.buoi2.ltw.dto.CreateOrderDTO;
import com.buoi2.ltw.dto.UpdateStatusOrderDTO;
import com.buoi2.ltw.entity.Account;
import com.buoi2.ltw.entity.Order;
import com.buoi2.ltw.entity.OrderDetail;
import com.buoi2.ltw.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ProductDAO productDAO;


    @Override
    public ResponseEntity<List<Order>> listAllOrder() {
        List<Order> listOrder = orderDAO.findAll();
        if (listOrder.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(listOrder, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> getOrderDetails(Long orderId) {
        Optional<Order> order = orderDAO.findById(orderId);
        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(order.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> addOrder(CreateOrderDTO createOrderDTO) throws Exception {
        Optional<Account> account = accountDAO.findByUsername(createOrderDTO.getUsername());
        if(account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Order order = new Order();
        order.setAddress(createOrderDTO.getAddress());
        order.setNumberPhone(createOrderDTO.getNumberPhone());
        order.setStatus("pending");
        order.setCreateDate(new Date());
        order.setAccount(account.get());
        double price = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CreateOrderDTO.OrderDetailDTO item : createOrderDTO.getOrderDetails()) {
            Optional<Product> product = productDAO.findById(item.getProductId());
            if(product.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Product existingProduct = product.get();
            if (existingProduct.getQuality() < item.getQuantity()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            existingProduct.setQuality(existingProduct.getQuality() - item.getQuantity());
            productDAO.save(existingProduct);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product.get());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(item.getQuantity() * existingProduct.getPrice());
            price += item.getQuantity() * existingProduct.getPrice();
            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        order.setTotalPrice(price);
        orderDAO.save(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> updateStatusOrder(Long orderId, UpdateStatusOrderDTO updateStatusOrderDTO) throws Exception {
        Optional<Order> order = orderDAO.findById(orderId);
        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Order orderExist = order.get();
        orderExist.setStatus(updateStatusOrderDTO.getStatus());
        if ("failed".equals(updateStatusOrderDTO.getStatus())) {
            List<OrderDetail> listOrderDetail = orderDetailDAO.findByOrderId(orderId);

            for (OrderDetail orderDetail : listOrderDetail) {
                Product product = orderDetail.getProduct();
                product.setQuality(product.getQuality() + orderDetail.getQuantity());
                productDAO.save(product);
            }
        }
        orderDAO.save(orderExist);
        return new ResponseEntity<>(orderExist, HttpStatus.OK);
    }
}
