package com.example.food_app.mappers;

import com.example.food_app.dto.restaurant.RestaurantRequest;
import com.example.food_app.dto.restaurant.RestaurantResponse;
import com.example.food_app.dto.restaurant.UpdateRestaurantRequest;
import com.example.food_app.models.Restaurant;
import com.example.food_app.models.User;

public class RestaurantMapper {
    public static RestaurantResponse toResponse(Restaurant restaurant){
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getOwner() != null ? restaurant.getOwner().getId() : null,
                restaurant.getOwner() != null ? restaurant.getOwner().getFullName() : null,
                restaurant.getName(),restaurant.getPhone(),restaurant.getAddress(),
                restaurant.getLatitude(),restaurant.getLongitude(),restaurant.getIsActive(),
                restaurant.getCreatedAt(),restaurant.getUpdatedAt()
        );
    }

    public static Restaurant toEntity(RestaurantRequest restaurantRequest, User owner){
        return Restaurant.builder()
                .owner(owner)
                .name(restaurantRequest.getName())
                .phone(restaurantRequest.getPhone())
                .address(restaurantRequest.getAddress())
                .latitude(restaurantRequest.getLatitude())
                .longitude(restaurantRequest.getLongitude())
                .isActive(restaurantRequest.getIsActive())
                .build();
    }
    public static void updateEntity(UpdateRestaurantRequest request, Restaurant restaurant) {
        if (request.getName() != null && !request.getName().isBlank()) {
            restaurant.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            restaurant.setPhone(request.getPhone());
        }
        if (request.getAddress() != null && !request.getAddress().isBlank()) {
            restaurant.setAddress(request.getAddress());
        }
        if (request.getLatitude() != null && !Double.isNaN(request.getLatitude())) {
            restaurant.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null && !Double.isNaN(request.getLongitude())) {
            restaurant.setLongitude(request.getLongitude());
        }
    }


}
