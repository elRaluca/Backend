package com.example.backend.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="specialBouquet_id",referencedColumnName = "id")
    private SpecialBouquet specialBouquet;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;
}