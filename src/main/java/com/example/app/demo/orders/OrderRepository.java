package com.example.app.demo.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserId(Long userId);

    List<OrderEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

    // ✅ NEW: used for rating validation
    @Query("""
        SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END
        FROM OrderEntity o
        JOIN o.items i
        WHERE o.user.id = :userId
        AND i.productId = :productId
    """)
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}
