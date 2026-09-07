package com.example.food_app.repository;

import com.example.food_app.models.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, UUID> {
    Page<MenuCategory> findByRestaurantId(UUID restaurantId, Pageable pageable);
    Page<MenuCategory> findByNameIsContainingIgnoreCase(String name, Pageable pageable);
}
