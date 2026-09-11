package com.example.food_app.mappers;

import com.example.food_app.dto.restaurant.MenuCategoryRequest;
import com.example.food_app.dto.restaurant.MenuCategoryResponse;
import com.example.food_app.dto.restaurant.MenuItemResponse;
import com.example.food_app.dto.restaurant.UpdateMenuCategoryRequest;
import com.example.food_app.models.MenuCategory;
import com.example.food_app.models.MenuItem;
import com.example.food_app.models.Restaurant;

import java.util.Collections;
import java.util.List;

public class MenuCategoryMapper {
    public static MenuCategoryResponse toResponse(MenuCategory menuCategory){
        List<MenuItemResponse> items = menuCategory.getMenuItems() != null ? menuCategory.getMenuItems().stream().map(MenuItemMapper::toResponse).toList() : Collections.emptyList();
        return new MenuCategoryResponse(
                menuCategory.getId(),
                menuCategory.getRestaurant() != null ? menuCategory.getRestaurant().getId() : null,
                menuCategory.getRestaurant() != null ? menuCategory.getRestaurant().getName() : null,
                menuCategory.getName(), items, menuCategory.getCreatedAt(), menuCategory.getUpdatedAt()
        );
    }
    public static MenuCategory toEntity(MenuCategoryRequest request, Restaurant restaurant){
        return MenuCategory.builder()
                .restaurant(restaurant)
                .name(request.getName())
                .build();
    }

    public static void updateEntity(UpdateMenuCategoryRequest request, MenuCategory existing){
        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());
    }
}
