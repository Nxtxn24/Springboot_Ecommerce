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
    public void rateProduct(String email, Long productId, int rating) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("USER ID: " + user.getId());
        System.out.println("PRODUCT ID: " + productId);

        System.out.println("PURCHASED? " +orderRepository.existsByUserIdAndProductId(user.getId(), productId));

        boolean purchased = orderRepository
                .existsByUserIdAndProductId(user.getId(), productId);

        if (!purchased) {
            throw new RuntimeException("Only purchased products can be rated");
        }

        Optional<ProductRating> existing =
                ratingRepository.findByUserIdAndProductId(user.getId(), productId);

        if (existing.isPresent()) {
            ProductRating r = existing.get();
            r.setRating(rating);
            ratingRepository.save(r);
        } else {
            ProductRating r = new ProductRating();
            r.setUser(user);
            r.setProduct(product);
            r.setRating(rating);
            ratingRepository.save(r);
        }
        

        updateProductAverage(product);
    }

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