package com.example.app.demo.orders;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/checkout")
    public OrderResponseDto checkout(Authentication authentication) {

        String email = authentication.getName();

        return orderService.checkout(email);
    }

    @GetMapping
    public List<OrderResponseDto> getUserOrders(Authentication authentication) {

        String email = authentication.getName();

        return orderService.getUserOrders(email);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderById(
            @PathVariable Long orderId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return orderService.getOrderById(email, orderId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{orderId}/status")
    public OrderEntity updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        
        return orderService.updateStatus(orderId, status);
    }
}
