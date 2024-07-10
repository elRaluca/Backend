package com.example.backend.controller;


import com.example.backend.dto.ProductDTO;
import com.example.backend.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminProductController {
    private ProductService productService;



    @PostMapping(value = "/createproduct", consumes = "multipart/form-data")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestPart("product") String productData,
            @RequestPart("image") MultipartFile image) {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO;
        try {
            productDTO = objectMapper.readValue(productData, ProductDTO.class);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getIsVisible() == null) {
            productDTO.setIsVisible(true);
        }


        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getPrice() == null || productDTO.getPrice() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getDescription() == null || productDTO.getDescription().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getName().length() > 20) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getDescription().length() > 250) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String imagePath = storeImage(image);
        productDTO.setImage(imagePath);

        ProductDTO savedProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


    private String storeImage(MultipartFile image) {
        String uploadDir = "src/main/resources/static/image";

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create the directory where the uploaded files will be stored.", e);
            }
        }

        String filename = StringUtils.cleanPath(image.getOriginalFilename());
        Path storagePath = uploadPath.resolve(filename);
        try {
            Files.copy(image.getInputStream(), storagePath, StandardCopyOption.REPLACE_EXISTING);
            return "/image/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }



    @PutMapping(value = "/update/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Integer productId,
                                                    @RequestPart("product") String productData,
                                                    @RequestPart(value = "image", required = false) MultipartFile image) throws JsonProcessingException {
        ProductDTO updateProduct = new ObjectMapper().readValue(productData, ProductDTO.class);
        if (image != null && !image.isEmpty()) {
            ProductDTO product = productService.getProductById(productId);
            productService.deleteProductImage(product.getImage());
            String imagePath = storeImage(image);
            updateProduct.setImage(imagePath);
        } else {
            ProductDTO currentProduct = productService.getProductById(productId);
            updateProduct.setImage(currentProduct.getImage());
        }

        if (updateProduct.getName().length() > 20 || updateProduct.getDescription().length() > 250) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductDTO productDTO = productService.updateProduct(productId, updateProduct);
        return ResponseEntity.ok(productDTO);
    }


    @PutMapping("/hiden/{id}")
    public ResponseEntity<String> hideProduct(@PathVariable("id") Integer productId) {
        productService.hideProduct(productId);
        return ResponseEntity.ok("Product hidden successfully");
    }

}


