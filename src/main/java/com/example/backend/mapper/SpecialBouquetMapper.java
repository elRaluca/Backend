package com.example.backend.mapper;

import com.example.backend.dto.SpecialBouquetDTO;
import com.example.backend.entity.SpecialBouquet;

public class SpecialBouquetMapper {
    public static SpecialBouquetDTO mapToSpecialBouquetDTO(SpecialBouquet specialBouquet){
        return new SpecialBouquetDTO(
                specialBouquet.getId(),
                specialBouquet.getImageBouquet(),
                specialBouquet.getPriceBouquet()
        );

    }


    public static SpecialBouquet mapToSpecialBouquet(SpecialBouquetDTO specialBouquetDTO){
        return new SpecialBouquet(
                specialBouquetDTO.getId(),
                specialBouquetDTO.getImageBouquet(),
                specialBouquetDTO.getPriceBouquet()
        );

    }
}
