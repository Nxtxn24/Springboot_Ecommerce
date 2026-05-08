package com.example.app.demo.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductRequestDto dto) {

        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // =========================
    // NEW PAGINATION METHOD
    // =========================
    @Override
    public Page<Product> getProducts(String search, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (search == null || search.isEmpty()) {
            return productRepository.findAll(pageable);
        }

        return productRepository.findByNameContainingIgnoreCase(search, pageable);
    }

    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDto dto) {

        Product existing = getProductById(id);

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setCategory(dto.getCategory());
        existing.setStockQuantity(dto.getStockQuantity());
        existing.setImageUrl(dto.getImageUrl());

        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}