package com.example.backend.repository;

import com.example.backend.entity.Order;
import com.example.backend.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findByDeliveryEmail(String email);
    List<Order> findByDeliveryName(String name);
    List<Order> findByDeliveryPhone(String phone);
    List<Order>  findByStatus(OrderStatus status);
    @Query("SELECT SUM(o.total) FROM Order o" +
            " WHERE MONTH(o.orderDate) = :month " +
            "AND YEAR(o.orderDate) = :year " +
            "AND o.status = com.example.backend.entity.OrderStatus.COMPLETED")
    Optional<Double> findTotalForMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.product p " +
            "LEFT JOIN FETCH oi.specialBouquet sb")
    List<Order> findAllWithItems();


}
