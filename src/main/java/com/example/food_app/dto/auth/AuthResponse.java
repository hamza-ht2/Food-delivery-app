package com.example.food_app.dto.auth;


import com.example.food_app.models.enums.Role;

public record AuthResponse(
        String token , String email, Role role
){}
