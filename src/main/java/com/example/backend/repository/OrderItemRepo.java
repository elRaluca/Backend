package com.example.backend.repository;

import com.example.backend.entity.Cart;
import com.example.backend.entity.Order;
import com.example.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi.product.id, SUM(oi.quantity) AS totalQuantity " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "WHERE p.isVisible = true " +
            "GROUP BY oi.product.id " +
            "ORDER BY totalQuantity DESC")
        List<Object[]> findTopSellingProducts(Pageable pageable);


    }


