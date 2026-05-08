package com.example.app.demo.orderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByProductId(Long productId);


    @Query("""
        SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END
        FROM OrderEntity o
        JOIN o.items i
        WHERE o.user.id = :userId
        AND i.productId = :productId
    """)
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}       