package com.example.backend.service;

import com.example.backend.dto.CartDTO;
import com.example.backend.dto.CartItemDTO;
import com.example.backend.entity.Cart;
import com.example.backend.entity.CartItem;
import com.example.backend.entity.Product;
import com.example.backend.entity.SpecialBouquet;
import com.example.backend.mapper.CartMapper;
import com.example.backend.repository.CartItemRepo;
import com.example.backend.repository.CartRepo;
import com.example.backend.repository.ProductRepo;
import com.example.backend.repository.SpecialBouquetRepo;
import io.jsonwebtoken.Jwt;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SpecialBouquetRepo specialBouquetRepo;





    @Transactional
    public CartDTO addProductToCart(Integer userId, Integer productId, int quantity) {
        // Retrieve the cart or create a new one if it doesn't exist
        Cart cart = cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return newCart;
                });

        // Save the new cart if it doesn't already have an ID (i.e., it's new)
        if (cart.getId() == null) {
            cart = cartRepo.save(cart);
        }

        // Find the product by ID or throw if not found
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Try to find an existing cart item for this product in the cart
        Optional<CartItem> existingCartItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingCartItem.isPresent()) {
            // Product is already in the cart, so just update the quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepo.save(cartItem);
        } else {
            // Product is not in the cart, so create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepo.save(cartItem);
        }

        // Convert cart to CartDTO and return
        return cartMapper.convertToCartDTO(cart);
    }


    @Transactional
    public CartDTO addSpecialProductToCart(Integer userId, Integer specialBouquetId, int quantity){
        Cart cart=cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart=new Cart();
                    newCart.setUserId(userId);
                    return newCart;
                });
        if(cart.getId()==null){
            cart=cartRepo.save(cart);
        }

        SpecialBouquet specialBouquet=specialBouquetRepo.findById(specialBouquetId)
                .orElseThrow(() -> new IllegalArgumentException("Special product not found"));
        CartItem cartItem=new CartItem();
        cartItem.setCart(cart);
        cartItem.setSpecialBouquet(specialBouquet);
        cartItem.setQuantity(quantity);
        cartItemRepo.save(cartItem);
        return  cartMapper.convertToCartDTO(cart);

    }

    public CartDTO getCartByUserId(Integer userId) {
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user with ID: " + userId));

        // Presupunând că ai o metodă în cartMapper pentru a converti Cart în CartDTO
        CartDTO cartDTO = cartMapper.convertToCartDTO(cart);

        // Iterate through each item in the cart and populate details based on whether it is a Product or a Special Bouquet
        for (CartItemDTO item : cartDTO.getItems()) {
            if (item.getProductId() != null) {
                // If the item is a product, fetch and set product details
                Product product = productRepo.findById(item.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                item.setProductName(product.getName());
                item.setProductPrice(product.getPrice());
                item.setProductImage(product.getImage());
             // Optionally set the type for clearer differentiation in client-side logic
            } else if (item.getSpecialBouquetId() != null) {
                // If the item is a special bouquet, fetch and set special bouquet details
                SpecialBouquet specialBouquet = specialBouquetRepo.findById(item.getSpecialBouquetId())
                        .orElseThrow(() -> new RuntimeException("Special Bouquet not found"));
                item.setProductPrice(specialBouquet.getPriceBouquet());
                item.setProductImage(specialBouquet.getImageBouquet());
                  // Optionally set the type for clearer differentiation in client-side logic
            } else {
                throw new IllegalStateException("Cart item neither linked to a product nor a special bouquet");
            }
        }

        return cartDTO;
    }


    @Transactional
    public double calculateTotalCartValue(Integer userId) {
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user with ID: " + userId));

        // Calculează suma totală pentru produse și buchete speciale
        return cart.getItems().stream()
                .mapToDouble(item -> {
                    // Verifică dacă item-ul este un produs standard
                    if (item.getProduct() != null) {
                        return item.getProduct().getPrice() * item.getQuantity();
                    }
                    // Altfel, verifică dacă item-ul este un buchet special
                    else if (item.getSpecialBouquet() != null) {
                        return item.getSpecialBouquet().getPriceBouquet() * item.getQuantity();
                    }
                    // În cazul în care item-ul nu este nici produs, nici buchet special, returnează 0
                    return 0.0;
                })
                .sum();
    }


    @Transactional
    public CartDTO incrementProductQuantityInCart(Integer userId, Integer productId) {
        // Găsește coșul utilizatorului sau aruncă o excepție dacă acesta nu există
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        // Găsește produsul sau aruncă o excepție dacă acesta nu există
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Caută elementul din coș (CartItem) pentru produsul specificat
        Optional<CartItem> existingCartItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingCartItem.isPresent()) {
            // Dacă produsul este deja în coș, actualizează cantitatea
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepo.save(cartItem);
        } else {
            // Aruncă o excepție dacă produsul nu este în coș
            throw new IllegalArgumentException("Product not in cart. Add the product to the cart first.");
        }

        // Convert cart to CartDTO and return
        return cartMapper.convertToCartDTO(cart);
    }

    @Transactional
    public CartDTO decrementProductQuantityInCart(Integer userId, Integer productId) {
        // Găsește coșul utilizatorului sau aruncă o excepție dacă acesta nu există
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        // Găsește produsul sau aruncă o excepție dacă acesta nu există
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Caută elementul din coș (CartItem) pentru produsul specificat
        Optional<CartItem> existingCartItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int currentQuantity = cartItem.getQuantity();
            if (currentQuantity > 1) {
                // Dacă cantitatea este mai mare decât 1, decrementăm cantitatea
                cartItem.setQuantity(currentQuantity - 1);
                cartItemRepo.save(cartItem);
            } else {
                // Dacă cantitatea este 1, putem alege să ștergem produsul din coș sau să lăsăm cantitatea neschimbată
                // Exemplu pentru ștergere:
                cartItemRepo.delete(cartItem);
                // sau pentru a lăsa cantitatea la 1, pur și simplu nu facem nicio modificare
            }
        } else {
            // Aruncă o excepție dacă produsul nu este în coș
            throw new IllegalArgumentException("Product not in cart. Add the product to the cart first.");
        }

        // Convert cart to CartDTO and return
        return cartMapper.convertToCartDTO(cart);
    }


    @Transactional
    public CartDTO removeProductFromCart(Integer userId, Integer productId) {
        // Găsește coșul utilizatorului sau aruncă o excepție dacă acesta nu există
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        // Caută elementul din coș (CartItem) pentru produsul specificat
        Optional<CartItem> cartItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), productId);

        if (cartItem.isPresent()) {
            // Dacă produsul este în coș, șterge elementul
            cartItemRepo.delete(cartItem.get());
            cartItemRepo.flush(); // Asigură-te că modificările sunt persistate.
            cart = cartRepo.findById(cart.getId()).orElseThrow(() -> new IllegalStateException("Cart not found after update"));

        } else {
            // Aruncă o excepție dacă produsul nu este în coș
            throw new IllegalArgumentException("Product not in cart.");
        }

        // Actualizează și returnează coșul actualizat
        return cartMapper.convertToCartDTO(cart);
    }








}
