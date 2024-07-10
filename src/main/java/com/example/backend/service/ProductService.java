package com.example.backend.service;


import com.example.backend.dto.ProductDTO;

import java.util.List;


public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO getProductById(Integer productId);

    List<ProductDTO> getAllProduct();

    ProductDTO updateProduct(Integer productId, ProductDTO updateProduct);


    void deleteProductImage(String image);

    void hideProduct(Integer productId);
}
