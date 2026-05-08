package com.example.app.demo.ratings;

public interface RatingService {
    void rateProduct(String email, Long productId, int rating);
}
