package com.example.app.demo.cart;

import java.util.List;

import com.example.app.demo.cartItem.CartItemResponse;

public class CartResponseDto {

    private Long cartId;
    private List<CartItemResponse> items;

    public CartResponseDto() {}

    public CartResponseDto(Long cartId, List<CartItemResponse> items) {
        this.cartId = cartId;
        this.items = items;
    }
    public Long getCartId() {
        return cartId;
    }
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
    public List<CartItemResponse> getItems() {
        return items;
    }
    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }
}
