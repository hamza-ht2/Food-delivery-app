package com.example.food_app.services.impl;

import com.example.food_app.dto.restaurant.MenuItemRequest;
import com.example.food_app.dto.restaurant.MenuItemResponse;
import com.example.food_app.dto.restaurant.UpdateMenuItemRequest;
import com.example.food_app.exceptions.ResourceNotFoundException;
import com.example.food_app.mappers.MenuItemMapper;
import com.example.food_app.models.MenuCategory;
import com.example.food_app.models.MenuItem;
import com.example.food_app.repository.MenuCategoryRepository;
import com.example.food_app.repository.MenuItemRepository;
import com.example.food_app.services.MenuItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuCategoryRepository categoryRepository;
    private final MenuItemRepository itemRepository;

    public MenuItemServiceImpl(MenuCategoryRepository categoryRepository, MenuItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public MenuItemResponse createMenuItem(MenuItemRequest request, UUID categoryId, String ownerEmail) {
        MenuCategory menuCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with id :"+categoryId));
        if (!menuCategory.getRestaurant().getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("you don't have the permission to create a new menu item");
        }
        MenuItem menuItem = MenuItemMapper.toEntity(request,menuCategory);
        MenuItem saved = itemRepository.save(menuItem);
        return MenuItemMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MenuItemResponse updateMenuItem(UUID menuItemId, UpdateMenuItemRequest request, String ownerEmail) {
        MenuItem menuItem = itemRepository.findById(menuItemId).orElseThrow(()-> new ResourceNotFoundException("item not found with id :"+menuItemId));
        if (!menuItem.getMenuCategory().getRestaurant().getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("access denied to update the item");
        }
        MenuItemMapper.updateEntity(request,menuItem);
        MenuItem saved = itemRepository.save(menuItem);
        return MenuItemMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MenuItemResponse toggleAvailability(UUID itemId, String ownerEmail) {
        MenuItem menuItem = itemRepository.findById(itemId).orElseThrow(()-> new ResourceNotFoundException("item not found with id :"+itemId));
        if (!menuItem.getMenuCategory().getRestaurant().getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("access denied");
        }
        menuItem.setIsAvailable(!menuItem.getIsAvailable());
        MenuItem saved = itemRepository.save(menuItem);
        return MenuItemMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteMenuItem(UUID itemId, String ownerEmail) {
        MenuItem menuItem = itemRepository.findById(itemId).orElseThrow(()-> new ResourceNotFoundException("item not found with id :"+itemId));
        if (!menuItem.getMenuCategory().getRestaurant().getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("access denied");
        }
        itemRepository.delete(menuItem);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuItemResponse getMenuItemById(UUID id) {
        MenuItem menuItem = itemRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("item not found with id :"+id));
        return MenuItemMapper.toResponse(menuItem);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuItemResponse> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable).map(MenuItemMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuItemResponse> getItemsByMenuCategoryId(UUID menuCategoryId, Pageable pageable) {
        return itemRepository.findByMenuCategoryId(menuCategoryId, pageable).map(MenuItemMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuItemResponse> getAvailableItemsByCategoryId(UUID menuCategoryId, Pageable pageable) {
        return itemRepository.findByMenuCategoryIdAndIsAvailableTrue(menuCategoryId, pageable).map(MenuItemMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuItemResponse> getItemsByPriceRange(BigDecimal min, BigDecimal max, Pageable pageable) {
        return itemRepository.findByPriceBetween(min, max, pageable).map(MenuItemMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuItemResponse> getItemsByName(String name, Pageable pageable) {
        return itemRepository.getMenuItemsByNameContainingIgnoreCase(name, pageable).map(MenuItemMapper::toResponse);
    }
}
