package com.example.food_app.services.impl;

import com.example.food_app.dto.user.*;
import com.example.food_app.exceptions.DuplicateResourceException;
import com.example.food_app.exceptions.ResourceNotFoundException;
import com.example.food_app.mappers.UserMapper;
import com.example.food_app.models.User;
import com.example.food_app.models.enums.Role;
import com.example.food_app.repository.UserRepository;
import com.example.food_app.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(String email) {
        User user = repository.findUserByEmail(email).orElseThrow(()-> new ResourceNotFoundException("user not found with email :"+email));
        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(String email, UpdateProfileRequest request) {
        User user = repository.findUserByEmail(email).orElseThrow(()-> new ResourceNotFoundException("user not found with email :"+email));
        UserMapper.updateEntity(request,user);
        User saved = repository.save(user);
        return UserMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public UserProfileResponse updateEmail(String email, UpdateEmailRequest request) {
        User user = repository.findUserByEmail(email).orElseThrow(()-> new ResourceNotFoundException("user not found with email :"+email));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadCredentialsException("bad credentials , enter the right password");
        }
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())){
            if (repository.existsByEmail(request.getEmail())){
                throw new DuplicateResourceException("email already in use");
            }
            user.setEmail(request.getEmail());
        }
        User saved = repository.save(user);
        return UserMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void updatePassword(String email, UpdatePasswordRequest request) {
        User user = repository.findUserByEmail(email).orElseThrow(()-> new ResourceNotFoundException("user not found with email :"+email));
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new BadCredentialsException("password incorrect");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new IllegalArgumentException("password do not match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);

    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getUserById(UUID id) {
        User user = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found with id :"+id));
        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        User user = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found with id :"+id));
        repository.delete(user);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileResponse> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable).map(UserMapper::toResponse);
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserRole(UUID id, UpdateRoleRequest request) {
        User user = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found with id :"+id));
        if (request.getRole().equals(user.getRole())){
            throw new DuplicateResourceException("role already assigned");
        }
        user.setRole(request.getRole());
        User saved = repository.save(user);
        return UserMapper.toResponse(saved);
    }
}
