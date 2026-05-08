package com.example.app.demo.cart;

public interface CartService {

    CartResponseDto getCart(String email);

    CartResponseDto addToCart(String email, Long productId, int quantity);

    CartResponseDto updateQuantity(String email, Long productId, int quantity);

    CartResponseDto removeItem(String email, Long productId);

    CartResponseDto clearCart(String email);

    CartResponseDto decreaseQuantity(String email, Long productId, int quantity);
}