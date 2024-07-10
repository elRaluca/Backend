package com.example.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private Integer id;
    private String name;
    private String contact;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String country;
    private String zipCode;

}
