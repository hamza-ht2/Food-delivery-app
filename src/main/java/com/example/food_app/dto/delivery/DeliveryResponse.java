package com.example.food_app.dto.delivery;

import com.example.food_app.models.enums.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryResponse(
        UUID id,
        UUID orderId,
        UUID driverId,
        String driverName,
        String driverPhone,
        DeliveryStatus status,
        LocalDateTime pickupTime,
        LocalDateTime deliveryTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
