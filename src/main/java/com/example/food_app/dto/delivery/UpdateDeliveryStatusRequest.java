package com.example.food_app.dto.delivery;

import com.example.food_app.models.enums.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeliveryStatusRequest {
    @NotNull(message = "delivery status is required")
    private DeliveryStatus status;
}
