package com.example.food_app.dto.restaurant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MenuItemResponse(
        UUID id,
        UUID menuCategoryId,
        String menuCategoryName,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        Boolean isAvailable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
