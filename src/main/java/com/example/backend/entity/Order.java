package com.example.backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "orders")
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idOrder;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private double total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Delivery delivery;
}

