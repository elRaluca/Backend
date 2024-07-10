package com.example.backend.controller;

import com.example.backend.dto.ProductDTO;
import com.example.backend.service.OrderService;
import com.example.backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class PublicProductController {
    private ProductService productService;
    private OrderService orderService;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Integer productId){
        ProductDTO productDTO = productService.getProductById(productId);
        if (productDTO != null) {
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> products=productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/top-selling")
    public List<ProductDTO> getTopSellingProducts(@RequestParam int limit) {
        List<Object[]> topSellingProducts = orderService.getTopSellingProducts(limit);
        List<ProductDTO> result = new ArrayList<>();
        for (Object[] record : topSellingProducts) {
            Integer productId = (Integer) record[0];
            Long totalQuantity = (Long) record[1];
            ProductDTO productDto = productService.getProductById(productId);
            productDto.setTotalQuantity(totalQuantity);
            result.add(productDto);
        }
        return result;
    }
}
