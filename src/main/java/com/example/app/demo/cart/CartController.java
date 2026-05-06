package com.example.app.demo.cart;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 📄 GET CART
    @GetMapping
    public CartResponseDto getCart(Authentication auth) {
        return cartService.getCart(auth.getName());
    }

    // ➕ ADD TO CART
    @PostMapping("/add/{productId}")
    public CartResponseDto addToCart(
            @PathVariable Long productId,
            @RequestParam int quantity,
            Authentication auth
    ) {
        return cartService.addToCart(auth.getName(), productId, quantity);
    }

    // ❌ REMOVE ITEM
    @DeleteMapping("/item/{productId}")
    public CartResponseDto removeItem(
            @PathVariable Long productId,
            Authentication auth
    ) {
        return cartService.removeItem(auth.getName(), productId);
    }

    // 🧹 CLEAR CART
    @DeleteMapping("/clear")
    public CartResponseDto clearCart(Authentication auth) {
        return cartService.clearCart(auth.getName());
    }


    @PatchMapping("/item/{productId}/decrease")
    public CartResponseDto decreaseQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity,
            Authentication auth
    ) {
        return cartService.decreaseQuantity(auth.getName(), productId, quantity);
    }
}