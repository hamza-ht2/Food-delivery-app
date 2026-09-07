package com.example.food_app.repository;

import com.example.food_app.models.Delivery;
import com.example.food_app.models.enums.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery , UUID> {
    Optional<Delivery> findByOrderId(UUID orderId);
    Page<Delivery> findByStatus(DeliveryStatus status, Pageable pageable);
    Page<Delivery> findByDriverIdAndStatus(UUID driverId, DeliveryStatus status, Pageable pageable);
}
