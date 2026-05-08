package com.example.app.demo.ratings;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.app.demo.orders.OrderRepository;
import com.example.app.demo.products.Product;
import com.example.app.demo.products.ProductRepository;
import com.example.app.demo.users.UserEntity;
import com.example.app.demo.users.UserRepository;

@Service
public class RatingServiceImpl implements RatingService {

    private final ProductRatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public RatingServiceImpl(
            ProductRatingRepository ratingRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository
    ) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void rateProduct(String email, Long productId, int ratingValue) {

        UserEntity user = getUser(email);
        Product product = getProduct(productId);

        validatePurchase(user.getId(), productId);

        saveOrUpdateRating(user, product, ratingValue);

        updateProductAverage(product);
    }

    // =========================
    // STEP 1: USER + PRODUCT
    // =========================

    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // =========================
    // STEP 2: PURCHASE CHECK (IMPORTANT PART)
    // =========================

    private void validatePurchase(Long userId, Long productId) {

        boolean purchased = orderRepository
                .existsByUserIdAndProductId(userId, productId);

        if (!purchased) {
            throw new RuntimeException("Only purchased products can be rated");
        }
    }

    // =========================
    // STEP 3: SAVE / UPDATE RATING
    // =========================

    private void saveOrUpdateRating(UserEntity user, Product product, int ratingValue) {

        Optional<ProductRating> existing =
                ratingRepository.findByUserIdAndProductId(user.getId(), product.getId());

        if (existing.isPresent()) {

            ProductRating r = existing.get();
            r.setRating(ratingValue);
            ratingRepository.save(r);

        } else {

            ProductRating r = new ProductRating();
            r.setUser(user);
            r.setProduct(product);
            r.setRating(ratingValue);

            ratingRepository.save(r);
        }
    }

    // =========================
    // STEP 4: UPDATE PRODUCT AVG
    // =========================

    private void updateProductAverage(Product product) {

        List<ProductRating> ratings =
                ratingRepository.findByProductId(product.getId());

        double avg = ratings.stream()
                .mapToInt(ProductRating::getRating)
                .average()
                .orElse(0.0);

        product.setAverageRating(avg);
        product.setRatingCount(ratings.size());

        productRepository.save(product);
    }
}