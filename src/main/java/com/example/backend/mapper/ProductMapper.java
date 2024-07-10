package com.example.backend.mapper;

import com.example.backend.dto.ProductDTO;
import com.example.backend.entity.Product;

public class ProductMapper {

    public static ProductDTO mapToProductDTO(Product product){
        return new ProductDTO(
         product.getId(),
         product.getName(),
         product.getPrice(),
         product.getDescription(),
                product.getImage(),
                null,
                product.getIsVisible()

        );
    }



    public static Product mapToProduct(ProductDTO productDTO) {
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getDescription(),
                productDTO.getImage(),
                productDTO.getIsVisible()
        );
    }

}
