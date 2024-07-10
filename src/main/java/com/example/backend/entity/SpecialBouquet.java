package com.example.backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "special_bouquet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialBouquet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String imageBouquet;
    @Column(nullable = false)
    private Double priceBouquet;


}
