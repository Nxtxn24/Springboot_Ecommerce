package com.example.app.demo.orderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 🔑 Get all items of a specific order
    List<OrderItem> findByOrderId(Long orderId);
}
