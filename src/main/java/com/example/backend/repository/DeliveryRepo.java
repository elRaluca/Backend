package com.example.backend.repository;
import com.example.backend.entity.Delivery;
import com.example.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepo extends JpaRepository<Delivery, Long> {

}
