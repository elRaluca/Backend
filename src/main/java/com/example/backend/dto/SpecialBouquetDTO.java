package com.example.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialBouquetDTO {
    private Integer id;
    private String imageBouquet;
    private Double priceBouquet;
}
