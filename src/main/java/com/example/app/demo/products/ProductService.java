package com.example.app.demo.products;

import java.util.List;

import org.springframework.data.domain.Page;

public interface ProductService {

    Product createProduct(ProductRequestDto product);

    Product getProductById(Long id);

    Page<Product> getProducts(int page, int size);

    Page<Product> getProducts(String search, int page, int size);

    Product updateProduct(Long id, ProductRequestDto product);

    void deleteProduct(Long id);
}
