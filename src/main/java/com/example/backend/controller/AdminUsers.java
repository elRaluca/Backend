/* package com.example.backend.controller;

import com.example.backend.dto.ProductDTO;
import com.example.backend.dto.ReqRes;
import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AdminUsers {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts() {
        return ResponseEntity.ok(productRepo.findAll());
    }


    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone() {
        return ResponseEntity.ok("User alone can access this Api only");
    }

    @GetMapping("/saveProduct")
    public ResponseEntity<Object> ProductRepo() {
        return ResponseEntity.ok("User alone can access this Api only");
    }

} */