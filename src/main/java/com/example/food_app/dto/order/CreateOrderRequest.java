package com.example.food_app.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class CreateOrderRequest {
    @NotNull(message = "restaurant id is required")
    private UUID restaurantId;
    @NotNull(message = "delivery address id is required")
    private UUID deliveryAddressId;
    @NotEmpty(message = "order must contain at least one item")
    private List<OrderItemRequest> items;
}
