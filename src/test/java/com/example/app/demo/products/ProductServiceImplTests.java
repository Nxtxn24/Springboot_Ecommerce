package com.example.app.demo.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProductCopiesStockAndPrice() {
        ProductRequestDto request = new ProductRequestDto(
                "Keyboard",
                "Mechanical keyboard",
                new BigDecimal("2499.99"),
                "Accessories",
                7
        );
        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product product = productService.createProduct(request);

        assertThat(product.getPrice()).isEqualByComparingTo("2499.99");
        assertThat(product.getStockQuantity()).isEqualTo(7);
    }
}
