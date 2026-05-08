package com.example.app.demo.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.demo.orders.OrderEntity;
import com.example.app.demo.orders.OrderResponseDto;
import com.example.app.demo.orders.OrderServiceImpl;
import com.example.app.demo.orders.OrderStatus;

@RestController
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderServiceImpl orderService;

    public AdminOrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    // Get ALL orders
    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/{orderId}/status")
    public OrderEntity updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        return orderService.updateStatus(orderId, status);
    }

    @GetMapping("/statuses")
    public List<String> getAllStatuses() {
        return Arrays.stream(OrderStatus.values())
                .map(Enum::name)
                .toList();
}
}
