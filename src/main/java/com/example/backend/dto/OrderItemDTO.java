package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Integer productId;
    private Integer specialBouquetId;
    private int quantity;
    private double price;
    private String productImage;
    private String specialBouquetImage;

}
