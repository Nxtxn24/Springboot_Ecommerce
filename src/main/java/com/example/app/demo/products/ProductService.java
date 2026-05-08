package com.example.app.demo.products;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductRequestDto product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Long id, ProductRequestDto product);

    void deleteProduct(Long id);
}
