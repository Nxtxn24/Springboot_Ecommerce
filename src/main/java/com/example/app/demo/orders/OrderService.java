package com.example.app.demo.orders;

import com.example.app.demo.users.UserEntity;
import java.util.List;

public interface OrderService {
    
    UserEntity getUser(String email);

    OrderResponseDto checkout(String email);

    List<OrderResponseDto> getUserOrders(String email);

    OrderResponseDto getOrderById(String email, Long orderId);

    OrderEntity updateStatus(Long orderId, OrderStatus newStatus);

    List<OrderResponseDto> getAllOrders();
}
