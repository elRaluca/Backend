package com.example.backend.repository;

import com.example.backend.entity.Cart;
import com.example.backend.entity.SpecialBouquet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialBouquetRepo extends JpaRepository <SpecialBouquet, Integer>{

}
