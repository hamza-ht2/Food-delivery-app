package com.example.food_app.controllers;

import com.example.food_app.dto.user.*;
import com.example.food_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.getProfile(userDetails.getUsername()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(@Valid @RequestBody UpdateProfileRequest request, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.updateProfile(userDetails.getUsername(), request));
    }

    @PatchMapping("/me/update-email")
    public ResponseEntity<String> updateEmail(@Valid @RequestBody UpdateEmailRequest request, @AuthenticationPrincipal UserDetails userDetails){
        userService.updateEmail(userDetails.getUsername(), request);
        return ResponseEntity.ok("email updated successfully ! pls login again with your new email ");
    }

    @PatchMapping("/me/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody UpdatePasswordRequest request, @AuthenticationPrincipal UserDetails userDetails){
        userService.updatePassword(userDetails.getUsername(), request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserProfileResponse>> getAllUsers(Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileResponse> updateRole(@PathVariable UUID id, @Valid @RequestBody UpdateRoleRequest request){
        return ResponseEntity.ok(userService.updateUserRole(id,request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
