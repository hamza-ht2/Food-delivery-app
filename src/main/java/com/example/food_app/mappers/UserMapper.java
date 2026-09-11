package com.example.food_app.mappers;

import com.example.food_app.dto.auth.RegisterRequest;
import com.example.food_app.dto.user.UpdateProfileRequest;
import com.example.food_app.dto.user.UserProfileResponse;
import com.example.food_app.models.User;

public class UserMapper {
    public static UserProfileResponse toResponse(User user){
        return new UserProfileResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(), user.getPhone(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt()
        );
    }

    public static User toEntity(RegisterRequest registerRequest){
        return User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .phone(registerRequest.getPhone())
                .role(registerRequest.getRole())
                .build();
    }
    public static void updateEntity(UpdateProfileRequest request, User existing){
        if (request.getFullName() != null || !request.getFullName().isBlank()) existing.setFullName(request.getFullName());
        if (request.getPhone() != null || !request.getPhone().isBlank()) existing.setPhone(request.getPhone());
    }
}
