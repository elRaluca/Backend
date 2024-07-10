package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Integer id;
    private Integer specialBouquetId;
    private Integer productId;
    private String productName;
    private Double productPrice;
    private String productImage;
    private int quantity;

    // Constructorul din CartItemDTO
    public CartItemDTO(Integer id, Integer productId, String productName, Double productPrice, String productImage, Integer specialBouquetId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.specialBouquetId = specialBouquetId;
        this.quantity = quantity;
    }

}
