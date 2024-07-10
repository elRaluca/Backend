package com.example.backend.service.impl;

import com.example.backend.dto.ProductDTO;
import com.example.backend.entity.Product;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.mapper.ProductMapper;
import com.example.backend.repository.ProductRepo;
import com.example.backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = ProductMapper.mapToProduct(productDTO);
        Product savedProduct = productRepo.save(product);
        return ProductMapper.mapToProductDTO(savedProduct);
    }

    @Override
    public ProductDTO getProductById(Integer productId) {
        Product product=productRepo.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product is not exisst with given id"+productId));
        return ProductMapper.mapToProductDTO(product);
    }

    // In ProductServiceImpl.java
    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepo.findAll();
        return products.stream()
                .filter(Product::getIsVisible)  // Filter products to only include visible ones
                .map(ProductMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Integer productId, ProductDTO updateProduct) {

        Product product=productRepo.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product is not exists with given id"+productId)
        );
        product.setName(updateProduct.getName());
        product.setPrice(updateProduct.getPrice());
        product.setDescription(updateProduct.getDescription());
        if (updateProduct.getImage() != null && !updateProduct.getImage().isEmpty()) {
            product.setImage(updateProduct.getImage());
        }
       Product updateProductObj=productRepo.save(product);
        return ProductMapper.mapToProductDTO(updateProductObj);
    }


    public void deleteProductImage(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image file", e);
        }
    }


    public void hideProduct(Integer productId) {
        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product does not exist with id: " + productId)
        );
        product.setIsVisible(false);
        productRepo.save(product);
    }

}

