package com.example.app.demo.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    
    @GetMapping
    public CartResponseDto getCart(Authentication auth) {
        return cartService.getCart(auth.getName());
    }

    
    @PostMapping("/add/{productId}")
    public CartResponseDto addToCart(
            @PathVariable Long productId,
            @RequestParam int quantity,
            Authentication auth
    ) {
        return cartService.addToCart(auth.getName(), productId, quantity);
    }

    @PutMapping("/update/{productId}")
        public ResponseEntity<CartResponseDto> updateQuantity(
                @PathVariable Long productId,
                @RequestBody CartQuantityRequest request,
                Authentication auth
        ) {

            CartResponseDto response = cartService.updateQuantity(
                    auth.getName(),
                    productId,
                    request.getQuantity()
            );

            return ResponseEntity.ok(response);
        }

    
    @DeleteMapping("/item/{productId}")
    public CartResponseDto removeItem(
            @PathVariable Long productId,
            Authentication auth
    ) {
        return cartService.removeItem(auth.getName(), productId);
    }

    
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