package com.example.food_app.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderItemResponse(
        UUID id,
        UUID orderId,
        String menuItemName,
        Integer quantity,
        BigDecimal priceAtPurchase,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
