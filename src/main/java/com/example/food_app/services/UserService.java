package com.example.food_app.services;

import com.example.food_app.dto.user.*;
import com.example.food_app.models.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserProfileResponse getProfile(String email);
    UserProfileResponse updateProfile(String email, UpdateProfileRequest request);
    UserProfileResponse updateEmail(String email, UpdateEmailRequest request);
    void updatePassword(String email, UpdatePasswordRequest request);
    UserProfileResponse getUserById(UUID id);
    void deleteUser(UUID id);
    Page<UserProfileResponse> getAllUsers(Pageable pageable);
    UserProfileResponse updateUserRole(UUID id, UpdateRoleRequest request);
}
