package com.example.food_app.mappers;

import com.example.food_app.dto.restaurant.MenuItemRequest;
import com.example.food_app.dto.restaurant.MenuItemResponse;
import com.example.food_app.dto.restaurant.UpdateMenuItemRequest;
import com.example.food_app.models.MenuCategory;
import com.example.food_app.models.MenuItem;

public class MenuItemMapper {
    public static MenuItemResponse toResponse(MenuItem menuItem){
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getMenuCategory() != null ? menuItem.getMenuCategory().getId() : null,
                menuItem.getMenuCategory() !=null ? menuItem.getMenuCategory().getName() : null,
                menuItem.getName(), menuItem.getDescription() , menuItem.getPrice(), menuItem.getImageUrl(),
                menuItem.getIsAvailable(), menuItem.getCreatedAt(), menuItem.getUpdatedAt()
        );
    }

    public static MenuItem toEntity(MenuItemRequest request, MenuCategory menuCategory){
        return MenuItem.builder()
                .menuCategory(menuCategory)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .isAvailable(request.getIsAvailable())
                .build();
    }
    public static void updateEntity(UpdateMenuItemRequest request, MenuItem existing) {
        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getPrice() != null) existing.setPrice(request.getPrice());
        if (request.getImageUrl() != null) existing.setImageUrl(request.getImageUrl());
    }
}
