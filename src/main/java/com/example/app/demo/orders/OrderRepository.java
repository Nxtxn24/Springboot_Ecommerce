package com.example.app.demo.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    // 🔑 Get all orders for a user
    List<OrderEntity> findByUserId(Long userId);

    // 🔑 Get orders sorted by latest first (useful for order history)
    List<OrderEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

    
}
