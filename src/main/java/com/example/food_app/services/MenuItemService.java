package com.example.food_app.services;

import com.example.food_app.dto.restaurant.MenuItemRequest;
import com.example.food_app.dto.restaurant.MenuItemResponse;
import com.example.food_app.dto.restaurant.UpdateMenuItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface MenuItemService {
    MenuItemResponse createMenuItem(MenuItemRequest request, UUID categoryId, String ownerEmail);
    MenuItemResponse updateMenuItem(UUID menuItemId, UpdateMenuItemRequest request, String ownerEmail);
    MenuItemResponse toggleAvailability(UUID itemId ,String ownerEmail);
    void deleteMenuItem(UUID itemId, String ownerEmail);
    MenuItemResponse getMenuItemById(UUID id);
    Page<MenuItemResponse> getAllItems(Pageable pageable);
    Page<MenuItemResponse> getItemsByMenuCategoryId(UUID menuCategoryId, Pageable pageable);
    Page<MenuItemResponse> getAvailableItemsByCategoryId(UUID menuCategoryId, Pageable pageable);
    Page<MenuItemResponse> getItemsByPriceRange(BigDecimal min , BigDecimal max , Pageable pageable);
    Page<MenuItemResponse> getItemsByName(String name, Pageable pageable);

}
