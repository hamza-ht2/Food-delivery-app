package com.example.food_app.services;

import com.example.food_app.dto.restaurant.RestaurantRequest;
import com.example.food_app.dto.restaurant.RestaurantResponse;
import com.example.food_app.dto.restaurant.UpdateRestaurantRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RestaurantService {
    RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest, String email);
    RestaurantResponse updateRestaurant(UUID id ,UpdateRestaurantRequest restaurantRequest, String ownerEmail);
    void toggleActiveStatus(UUID id, String ownerEmail);
    Page<RestaurantResponse> getActiveRestaurants(Pageable pageable);
    Page<RestaurantResponse> searchActiveRestaurantsByName(String name, Pageable pageable);
    void deleteRestaurant(UUID id, String ownerEmail);
    Page<RestaurantResponse> getAllRestaurants(Pageable pageable);
    Page<RestaurantResponse> getRestaurantsByOwnerId(UUID id , Pageable pageable);
    RestaurantResponse getRestaurantById(UUID id);

}
