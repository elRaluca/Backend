package com.example.backend.controller;
import com.example.backend.dto.CartDTO;
import com.example.backend.dto.SpecialBouquetDTO;
import com.example.backend.repository.SpecialBouquetRepo;
import com.example.backend.service.CartService;
import com.example.backend.service.SpecialBouquetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserSpecialBouquetController {
    @Autowired
    private CartService cartService;

    @Autowired
    SpecialBouquetService specialBouquetService;

    @Autowired
    SpecialBouquetRepo specialBouquetRepo;


    @PostMapping(value = "/createSpecialBouquet", consumes = "multipart/form-data")
    public ResponseEntity<SpecialBouquetDTO> createSpecialBouquet(
            @RequestPart("specialBouquet") String specialBouquetJSON,
            @RequestPart("image") String base64Image) {
        ObjectMapper objectMapper = new ObjectMapper();
        SpecialBouquetDTO specialBouquetDTO;
        try {
            specialBouquetDTO = objectMapper.readValue(specialBouquetJSON, SpecialBouquetDTO.class);
            String imagePath = specialBouquetService.storeImage(base64Image);
            specialBouquetDTO.setImageBouquet(imagePath);
            SpecialBouquetDTO createdSpecialBouquetDTO = specialBouquetService.createSpecialBouquet(specialBouquetDTO);
            return new ResponseEntity<>(createdSpecialBouquetDTO, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addSpecialBouquet")
    public ResponseEntity<CartDTO> addSpecialBouquetToCart(@RequestParam Integer userId,
                                                           @RequestParam Integer specialBouquetId,
                                                           @RequestParam int quantity) {
        CartDTO cartDTO = cartService.addSpecialProductToCart(userId, specialBouquetId, quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

}
