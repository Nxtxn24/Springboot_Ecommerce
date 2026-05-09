package com.example.app.demo.wishlist;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.demo.products.Product;
import com.example.app.demo.products.ProductRepository;
import com.example.app.demo.users.UserEntity;
import com.example.app.demo.users.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistService {

    private final WishlistRepository repo;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishlistService(
        WishlistRepository repo,
        UserRepository userRepository,
        ProductRepository productRepository
    ) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void toggleWishlist(String email, Long productId) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow();

        Product product = productRepository.findById(productId)
                .orElseThrow();

        boolean exists = repo.existsByUserIdAndProductId(user.getId(), productId);

        if (exists) {
            repo.deleteByUserIdAndProductId(user.getId(), productId);
        } else {
            WishlistItem item = new WishlistItem();
            item.setUser(user);
            item.setProduct(product);
            repo.save(item);
        }
    }

    public List<Product> getWishlist(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow();

        return repo.findByUserId(user.getId())
                .stream()
                .map(WishlistItem::getProduct)
                .toList();
    }
}
