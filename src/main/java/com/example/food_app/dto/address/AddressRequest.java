package com.example.food_app.dto.address;

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
public class AddressRequest {
    @NotNull(message = "latitude is required")
    private Double latitude;
    @NotNull(message = "longitude is required")
    private Double longitude;
    @NotBlank(message = "street is required")
    private String streetAddress;
    private String buildingOrFloor;
    @NotNull(message = "isSavedForLater is required")
    private boolean isSavedForLater;
}
