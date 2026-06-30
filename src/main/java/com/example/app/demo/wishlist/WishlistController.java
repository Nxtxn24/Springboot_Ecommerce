package com.example.app.demo.wishlist;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.demo.products.Product;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @PostMapping("/{productId}")
    public void toggle(@PathVariable Long productId, Authentication auth) {
        service.toggleWishlist(auth.getName(), productId);
    }

    @GetMapping
    public List<Product> getWishlist(Authentication auth) {
        return service.getWishlist(auth.getName());
    }
}
