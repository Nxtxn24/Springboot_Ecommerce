package com.example.app.demo.cart;

import java.util.List;

import org.springframework.stereotype.Component;
import com.example.app.demo.cartItem.CartItem;
import com.example.app.demo.cartItem.CartItemResponse;

@Component
public class CartMapper {

    public CartResponseDto toDto(Cart cart) {

        CartResponseDto dto = new CartResponseDto();
        dto.setCartId(cart.getId());

        List<CartItemResponse> items = cart.getItems()
                .stream()
                .map(this::toItemDto)
                .toList();

        dto.setItems(items);

        return dto;
    }

    private CartItemResponse toItemDto(CartItem item) {

        CartItemResponse dto = new CartItemResponse();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());

        return dto;
    }
}
