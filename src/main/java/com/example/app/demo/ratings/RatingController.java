package com.example.app.demo.ratings;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.demo.products.Product;
import com.example.app.demo.products.ProductService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final ProductService productService;

    public RatingController(RatingService ratingService, ProductService productService) {
        this.ratingService = ratingService;
        this.productService = productService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> rateProduct(
            @PathVariable Long productId,
            @RequestParam int rating,
            Authentication auth
    ) {
        ratingService.rateProduct(auth.getName(), productId, rating);
         Product updated = productService.getProductById(productId);
        return ResponseEntity.ok(updated);
    }
}
