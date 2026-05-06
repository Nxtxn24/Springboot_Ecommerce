package com.example.app.demo.orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.demo.cart.Cart;
import com.example.app.demo.cart.CartRepository;
import com.example.app.demo.cartItem.CartItem;
import com.example.app.demo.orderItem.OrderItem;
import com.example.app.demo.orderItem.OrderItemDto;
import com.example.app.demo.users.UserEntity;
import com.example.app.demo.users.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    // Get user by email
    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    private OrderResponseDto mapToDTO(OrderEntity order) {

        OrderResponseDto dto = new OrderResponseDto();

        dto.setOrderId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().toString());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDto> itemDTOs = new ArrayList<>();

        for (OrderItem item : order.getItems()) {

            OrderItemDto itemDTO = new OrderItemDto();

            itemDTO.setProductId(item.getProductId());
            itemDTO.setProductName(item.getProductName());
            itemDTO.setPriceAtPurchase(item.getPriceAtPurchase());
            itemDTO.setQuantity(item.getQuantity());

            itemDTOs.add(itemDTO);
        }

        dto.setItems(itemDTOs);

        return dto;
    }

    // 1. CHECKOUT (Cart → Order)
    public OrderResponseDto checkout(String email) {

        UserEntity user = getUser(email);

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem cartItem : cart.getItems()) {

            OrderItem item = new OrderItem();

            item.setOrder(order); // IMPORTANT (owning side)

            item.setProductId(cartItem.getProduct().getId());
            item.setProductName(cartItem.getProduct().getName());
            item.setPriceAtPurchase(cartItem.getProduct().getPrice());
            item.setQuantity(cartItem.getQuantity());

            total += item.getPriceAtPurchase() * item.getQuantity();

            orderItems.add(item);
        }

        // set relationship
        order.getItems().addAll(orderItems);
        order.setTotalAmount(total);

        OrderEntity savedOrder = orderRepository.save(order);

        // 🧹 clear cart after successful checkout
        cart.getItems().clear();
        cartRepository.save(cart);

        return mapToDTO(savedOrder);
    }

    //  2. Get all orders for a user
    public List<OrderResponseDto> getUserOrders(String email) {

        UserEntity user = getUser(email);

        List<OrderEntity> orders =
            orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        return orders.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // 3. Get single order (with ownership check)
    public OrderResponseDto getOrderById(String email, Long orderId) {

        UserEntity user = getUser(email);

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return mapToDTO(order);
    }

    //  4. Update order status (admin/internal use)
    public OrderEntity updateStatus(Long orderId, OrderStatus newStatus) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        validateStatusTransition(order.getStatus(), newStatus);
        order.setStatus(newStatus);

        return orderRepository.save(order);
    }


    private void validateStatusTransition(OrderStatus current, OrderStatus next) {

        switch (current) {

            case PENDING -> {
                if (next != OrderStatus.CONFIRMED && next != OrderStatus.CANCELLED) {
                    throw new RuntimeException("Invalid transition from PENDING");
                }
            }

            case CONFIRMED -> {
                if (next != OrderStatus.SHIPPED && next != OrderStatus.CANCELLED) {
                    throw new RuntimeException("Invalid transition from CONFIRMED");
                }
            }

            case SHIPPED -> {
                if (next != OrderStatus.DELIVERED) {
                    throw new RuntimeException("Invalid transition from SHIPPED");
                }
            }

            case DELIVERED -> {
                throw new RuntimeException("DELIVERED order cannot be changed");
            }

            case CANCELLED -> {
                throw new RuntimeException("CANCELLED order cannot be changed");
            }
        }
    }
}