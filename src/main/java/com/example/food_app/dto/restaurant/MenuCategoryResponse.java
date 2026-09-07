package com.example.food_app.dto.restaurant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MenuCategoryResponse(
        UUID id,
        UUID restaurantId,
        String restaurantName,
        String name,
        List<MenuItemResponse> menuItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
