package com.example.backend.controller;

import com.example.backend.dto.OrderDTO;
import com.example.backend.entity.DeliveryStatus;
import com.example.backend.entity.OrderStatus;
import com.example.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")

public class AdminOrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrdersWithDetails();
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Integer orderId, @RequestParam OrderStatus status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }


    @PutMapping("/{orderId}/deliveryStatus")
    public ResponseEntity<OrderDTO> updateDeliveryStatus(
            @PathVariable Integer orderId,
            @RequestParam DeliveryStatus status) {

        try {
            // Apel la serviciul care efectuează actualizarea statusului de livrare
            OrderDTO updatedOrder = orderService.updateDeliveryStatus(orderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalStateException e) {
            // Gestionarea cazului în care comanda nu este găsită
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Gestionarea altor posibile excepții
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/orders/search")
    public ResponseEntity<List<OrderDTO>> searchOrders(@RequestParam String query) {
        List<OrderDTO> orders;
        if (query.matches("\\d+")) {
            orders = orderService.getOrdersByOrderId(Integer.parseInt(query));;
        } else if (query.contains("@")) { // Dacă conține '@', caută după email
            orders = orderService.getOrdersByEmail(query);
        } else if (query.matches("\\+?\\d+")) { // Dacă este număr de telefon
            orders = orderService.getOrdersByPhone(query);
        } else if (Arrays.stream(OrderStatus.values()).anyMatch((e) -> e.name().equalsIgnoreCase(query))) {
            orders = orderService.getOrdersByStatus(query.toUpperCase());
        } else { // În alt caz, caută după nume
            orders = orderService.getOrdersByName(query);
        }
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/total-for-month")
    public ResponseEntity<Double> getTotalForMonth(@RequestParam int year, @RequestParam int month) {
        Optional<Double> total = orderService.getTotalForMonth(year, month);
        return total.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

