package com.example.backend.mapper;

import com.example.backend.dto.CartDTO;
import com.example.backend.dto.CartItemDTO;
import com.example.backend.entity.Cart;
import com.example.backend.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CartMapper {

    public CartDTO convertToCartDTO(Cart cart) {
        List<CartItemDTO> items;
        if (cart.getItems() != null) {
            items = cart.getItems().stream()
                    .map(this::convertItemToDto)
                    .collect(Collectors.toList());
        } else {
            items = new ArrayList<>();
        }
        return new CartDTO(cart.getId(), items, cart.getUserId());
    }


    public CartItemDTO convertItemToDto(CartItem cartItem) {
        Integer productId = cartItem.getProduct() != null ? cartItem.getProduct().getId() : null;
        String productName = cartItem.getProduct() != null ? cartItem.getProduct().getName() : null;
        Double productPrice = cartItem.getProduct() != null ? cartItem.getProduct().getPrice() : null;
        String productImage = cartItem.getProduct() != null ? cartItem.getProduct().getImage() : null;
        Integer specialBouquetId = cartItem.getSpecialBouquet() != null ? cartItem.getSpecialBouquet().getId() : null;

        return new CartItemDTO(
                cartItem.getId(),
                productId,
                productName,
                productPrice,
                productImage,
                specialBouquetId,
                cartItem.getQuantity()
        );
    }



}




