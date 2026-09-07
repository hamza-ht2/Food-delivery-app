package com.example.food_app.dto.user;

import com.example.food_app.models.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfileResponse(
        UUID id , String fullName, String email , String phone , Role role, LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
