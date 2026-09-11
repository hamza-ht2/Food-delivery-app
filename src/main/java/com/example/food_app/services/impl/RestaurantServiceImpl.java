package com.example.food_app.services.impl;

import com.example.food_app.dto.restaurant.RestaurantRequest;
import com.example.food_app.dto.restaurant.RestaurantResponse;
import com.example.food_app.dto.restaurant.UpdateRestaurantRequest;
import com.example.food_app.exceptions.ResourceNotFoundException;
import com.example.food_app.mappers.RestaurantMapper;
import com.example.food_app.models.Restaurant;
import com.example.food_app.models.User;
import com.example.food_app.repository.RestaurantRepository;
import com.example.food_app.repository.UserRepository;
import com.example.food_app.services.RestaurantService;
import com.example.food_app.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new ResourceNotFoundException("user not found with email :"+email));
        Restaurant restaurant = RestaurantMapper.toEntity(restaurantRequest,user);
        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public RestaurantResponse updateRestaurant(UUID id, UpdateRestaurantRequest restaurantRequest, String ownerEmail) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("restaurant not found with id :" + id));

        if (!restaurant.getOwner().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException("you don't have the permission to modify");
        }

        RestaurantMapper.updateEntity(restaurantRequest, restaurant);
        Restaurant saved = restaurantRepository.save(restaurant);
        return RestaurantMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void toggleActiveStatus(UUID id, String ownerEmail) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("restaurant not found with id :"+id));
        if (!restaurant.getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("you do not have the permission to modify");
        }
        restaurant.setIsActive(!restaurant.getIsActive());
        restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantResponse> getActiveRestaurants(Pageable pageable) {
        return restaurantRepository.findByIsActiveTrue(pageable).map(RestaurantMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantResponse> searchActiveRestaurantsByName(String name, Pageable pageable) {
        return restaurantRepository.findByNameIsContainingIgnoreCaseAndIsActiveTrue(name, pageable).map(RestaurantMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteRestaurant(UUID id, String ownerEmail) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("restaurant not found with id :"+id));
        if (!restaurant.getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("access denied , cannot delete the restaurant");
        }
        restaurantRepository.delete(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantResponse> getAllRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable).map(RestaurantMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantResponse> getRestaurantsByOwnerId(UUID id, Pageable pageable) {
        return restaurantRepository.findByOwnerId(id, pageable).map(RestaurantMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponse getRestaurantById(UUID id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("restaurant not found with id :"+id));
        return RestaurantMapper.toResponse(restaurant);
    }
}
