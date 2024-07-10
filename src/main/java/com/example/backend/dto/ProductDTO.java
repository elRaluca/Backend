package com.example.backend.dto;


import com.example.backend.service.ProductService;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String image;
    private Long totalQuantity;
    private Boolean isVisible;


}
