package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
    public class CartDTO {
        private  Long id;
        private List<CartItemDTO> items;
        private Integer userId;


    }
