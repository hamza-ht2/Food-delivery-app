package com.example.food_app.dto.address;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressResponse(
        UUID id,
        String userFullName,
        Double latitude,
        Double longitude,
        String streetAddress,
        String buildingOrFloor,
        boolean isSavedForLater,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
