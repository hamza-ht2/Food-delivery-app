package com.example.food_app.dto.restaurant;

import java.time.LocalDateTime;
import java.util.UUID;

public record RestaurantResponse(
        UUID id ,
        UUID ownerId,
        String ownerName,
        String name,
        String phone,
        String address,
        Double latitude ,
        Double longitude,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
