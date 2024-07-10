package com.example.backend.controller;

import com.example.backend.dto.OrderDTO;
import com.example.backend.entity.Order;
import com.example.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserOrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/placeorder/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable Long userId, @RequestBody OrderDTO orderDetails) {
        try {
            Order order = orderService.createOrderFromCartWithDelivery(userId, orderDetails);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    @GetMapping("/oders")
    public ResponseEntity<List<OrderDTO>>  getAllOrders(){
        List<OrderDTO> orders=orderService. getAllOrders();
        return ResponseEntity.ok(orders);

    }



}
