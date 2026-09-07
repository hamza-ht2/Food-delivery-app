package com.example.food_app.dto.restaurant;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "description is required")
    private String description;
    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0",inclusive = false, message = "price must be greater than zero")
    private BigDecimal price;
    private String imageUrl;
    @NotNull(message = "isAvailable is required")
    private Boolean isAvailable;
}
