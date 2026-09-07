package com.example.food_app.repository;

import com.example.food_app.models.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Page<Restaurant> findByOwnerId(UUID ownerId, Pageable pageable);
    Page<Restaurant> findByIsActiveTrue(Pageable pageable);
    Page<Restaurant> findByNameIsContainingIgnoreCaseAndIsActiveTrue(String name, Pageable pageable);
}
