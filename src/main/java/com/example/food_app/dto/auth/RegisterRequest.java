package com.example.food_app.dto.auth;

import com.example.food_app.models.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "full name is required")
    private String fullName;
    @NotBlank(message = "email is required")
    @Email(message = "email format not valid")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 8, max = 16, message = "password must be at least 8 characters")
    private String password;
    @NotNull(message = "role is required")
    private Role role;
    @NotBlank(message = "phone is required" )
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;
}
