package com.example.food_app.services;

import com.example.food_app.dto.auth.AuthResponse;
import com.example.food_app.dto.auth.LoginRequest;
import com.example.food_app.dto.auth.RegisterRequest;
import com.example.food_app.dto.user.UserProfileResponse;

public interface AuthService {
    UserProfileResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
