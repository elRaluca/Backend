package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
