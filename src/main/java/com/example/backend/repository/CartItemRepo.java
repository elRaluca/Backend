package com.example.backend.repository;


import com.example.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Integer productId);
}
