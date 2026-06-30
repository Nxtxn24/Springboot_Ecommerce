package com.example.app.demo.cart;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.app.demo.products.Product;
import com.example.app.demo.products.ProductRepository;
import com.example.app.demo.users.UserEntity;
import com.example.app.demo.users.UserRepository;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTests {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void addToCartRejectsQuantityAboveAvailableStock() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        Cart cart = new Cart();
        cart.setUser(user);
        Product product = new Product(
                "Mouse",
                "Wireless mouse",
                new BigDecimal("799.00"),
                2,
                "Accessories"
        );
        product.setId(5L);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> cartService.addToCart("user@example.com", 5L, 3))
                .isInstanceOfSatisfying(ResponseStatusException.class, exception ->
                        org.assertj.core.api.Assertions.assertThat(exception.getStatusCode())
                                .isEqualTo(HttpStatus.CONFLICT)
                );
        verify(cartRepository, never()).save(cart);
    }
}
