package com.example.food_app.dto.order;

import com.example.food_app.dto.address.AddressResponse;
import com.example.food_app.models.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID customerId,
        String customerFullName,
        UUID restaurantId,
        String restaurantName,
        AddressResponse deliveryAddress,
        OrderStatus status,
        BigDecimal totalAmount,
        List<OrderItemResponse> orderItemResponses,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
