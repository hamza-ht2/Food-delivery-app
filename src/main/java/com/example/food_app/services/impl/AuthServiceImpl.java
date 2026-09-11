package com.example.food_app.services.impl;

import com.example.food_app.dto.auth.AuthResponse;
import com.example.food_app.dto.auth.LoginRequest;
import com.example.food_app.dto.auth.RegisterRequest;
import com.example.food_app.dto.user.UserProfileResponse;
import com.example.food_app.exceptions.DuplicateResourceException;
import com.example.food_app.mappers.UserMapper;
import com.example.food_app.models.User;
import com.example.food_app.repository.UserRepository;
import com.example.food_app.security.JwtUtils;
import com.example.food_app.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public AuthServiceImpl(JwtUtils jwtUtils,AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository repository) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public UserProfileResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException("email already in use");
        }
        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User saved = repository.save(user);
        return UserMapper.toResponse(saved);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        User user = repository.findUserByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("user not found with email :"+request.getEmail()));
        String token = jwtUtils.generateToken(request.getEmail());
        return new AuthResponse(token, user.getEmail(), user.getRole());
    }
}
