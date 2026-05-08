package com.example.app.demo.cart;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.app.demo.cartItem.CartItem;
import com.example.app.demo.cartItem.CartItemResponse;
import com.example.app.demo.products.Product;
import com.example.app.demo.products.ProductRepository;
import com.example.app.demo.users.UserEntity;
import com.example.app.demo.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    
    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    
    private Cart getOrCreateCart(UserEntity user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    
    public CartResponseDto getCart(String email) {
        UserEntity user = getUser(email);
        Cart cart = getOrCreateCart(user);

        return cartMapper.toDto(cart);
    }

    
    public CartResponseDto addToCart(String email, Long productId, int quantity) {

        UserEntity user = getUser(email);
        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // check existing item
        Optional<CartItem> existingItem = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(
                    existingItem.get().getQuantity() + quantity
            );
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        Cart saved = cartRepository.save(cart);
        return cartMapper.toDto(saved);
    }

    public CartResponseDto updateQuantity(String email, Long productId, int quantity){

            UserEntity user = getUser(email);

            Cart cart = cartRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Cart not found"));

            CartItem item = cart.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            if (quantity <= 0) {

                cart.getItems().removeIf(
                        i -> i.getProduct().getId().equals(productId)
                );

            } else {

                item.setQuantity(quantity);
            }

            Cart updatedCart = cartRepository.save(cart);

            return cartMapper.toDto(updatedCart);
        }

    
    public CartResponseDto removeItem(String email, Long productId) {

        UserEntity user = getUser(email);
        Cart cart = getOrCreateCart(user);

        cart.getItems().removeIf(item ->
                item.getProduct().getId().equals(productId)
        );

        Cart saved = cartRepository.save(cart);
        return cartMapper.toDto(saved);
    }

    
    public CartResponseDto clearCart(String email) {

        UserEntity user = getUser(email);
        Cart cart = getOrCreateCart(user);

        cart.getItems().clear();

        Cart saved = cartRepository.save(cart);
        return cartMapper.toDto(saved);
    }


    public CartResponseDto decreaseQuantity(String email, Long productId, int quantityToRemove) {

        UserEntity user = getUser(email);
        Cart cart = getOrCreateCart(user);

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        int newQuantity = item.getQuantity() - quantityToRemove;

        if (newQuantity > 0) {
            item.setQuantity(newQuantity);
        } else {
            
            cart.getItems().removeIf(
                i -> i.getProduct().getId().equals(productId)
            );
            
        }

        Cart saved = cartRepository.save(cart);
        return cartMapper.toDto(saved);
    }
}