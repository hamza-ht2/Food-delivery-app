package com.example.food_app.repository;

import com.example.food_app.models.Order;
import com.example.food_app.models.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByCustomerIdOrderByCreatedAtDesc(UUID orderId, Pageable pageable);
    Page<Order> findByRestaurantIdAndStatus(UUID restaurantId, OrderStatus status, Pageable pageable);
    Page<Order> findByRestaurantId(UUID restaurantId,Pageable pageable);
}
