package com.example.food_app.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRestaurantRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "phone is required")
    private String phone;
    @NotBlank(message = "address is required")
    private String address;
    @NotNull(message = "latitude is required")
    private Double latitude;
    @NotNull(message = "longitude is required")
    private Double longitude;

}
