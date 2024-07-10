package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private Date orderDate;
    private double total;
    private String status;
    private List<OrderItemDTO> items;
    private DeliveryDTO delivery;



}
