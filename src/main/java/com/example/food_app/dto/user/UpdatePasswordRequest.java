package com.example.food_app.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequest {
    @NotBlank(message = "old password is required")
    private String oldPassword;
    @NotBlank(message = "new password is required")
    @Size(min = 8, max = 16, message = "password must be at least 8 characters")
    private String newPassword;
    @NotBlank(message = "password is required")
    private String confirmPassword;
}
