package com.example.backend.controller;

import com.example.backend.dto.CartDTO;
import com.example.backend.entity.Cart;
import com.example.backend.mapper.CartMapper;
import com.example.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicCartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartMapper cartMapper;


    @PostMapping("/add")
    public ResponseEntity<CartDTO> addProductToCart(@RequestParam Integer userId,
                                                    @RequestParam Integer productId, @RequestParam int quantity) {
        CartDTO cartDTO = cartService.addProductToCart(userId, productId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable("userId") Integer userId) {
        try {
            // Directly get the fully populated CartDTO from the service
            CartDTO cartDTO = cartService.getCartByUserId(userId);
            return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Handle the case where the cart is not found or another error occurs
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/totalPrice/{userId}")
    public ResponseEntity<Double> getTotalCartValue(@PathVariable Integer userId) {
        try {
            double total = cartService.calculateTotalCartValue(userId);
            return ResponseEntity.ok(total);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/increment/{userId}/{productId}")
    public ResponseEntity<CartDTO> incrementProductInCart(@PathVariable Integer userId, @PathVariable Integer productId) {
        try {
            CartDTO updatedCart = cartService.incrementProductQuantityInCart(userId, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);

        }
    }

    @GetMapping("/decrement/{userId}/{productId}")
    public ResponseEntity<CartDTO> decrementProductInCart(@PathVariable Integer userId, @PathVariable Integer productId) {
        try {
            CartDTO updatedCart = cartService.decrementProductQuantityInCart(userId, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("/removeProductToCart/{userId}/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Integer userId, @PathVariable Integer productId) {
        try {
            CartDTO updatedCart = cartService.removeProductFromCart(userId, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}