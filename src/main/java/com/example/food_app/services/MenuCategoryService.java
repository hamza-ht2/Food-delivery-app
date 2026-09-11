package com.example.food_app.services;

import com.example.food_app.dto.restaurant.MenuCategoryRequest;
import com.example.food_app.dto.restaurant.MenuCategoryResponse;
import com.example.food_app.dto.restaurant.UpdateMenuCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MenuCategoryService {
    MenuCategoryResponse createCategory(MenuCategoryRequest request, UUID restaurantId, String ownerEmail);
    MenuCategoryResponse updateCategory(UUID categoryId, UpdateMenuCategoryRequest request, String ownerEmail);
    void deleteMenuCategory(UUID categoryId, String ownerEmail);

    Page<MenuCategoryResponse> getAllMenuCategories(Pageable pageable);
    Page<MenuCategoryResponse> getCategoriesByRestaurantId(UUID restaurantId, Pageable pageable);
    Page<MenuCategoryResponse> searchCategoriesByName(String name , Pageable pageable);
    MenuCategoryResponse getCategoryById(UUID categoryId);
}
