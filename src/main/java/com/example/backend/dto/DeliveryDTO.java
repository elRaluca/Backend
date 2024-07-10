package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    private Long id;
    private Long orderId;
    private String deliveryAddress;
    private String status;
    private String email;
    private String name;
    private String phone;
    private String city;
    private String country;
    private String street;
    private String deliveryMethod;
}
