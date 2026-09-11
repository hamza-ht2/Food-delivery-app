package com.example.food_app.services.impl;

import com.example.food_app.dto.restaurant.MenuCategoryRequest;
import com.example.food_app.dto.restaurant.MenuCategoryResponse;
import com.example.food_app.dto.restaurant.UpdateMenuCategoryRequest;
import com.example.food_app.exceptions.ResourceNotFoundException;
import com.example.food_app.mappers.MenuCategoryMapper;
import com.example.food_app.models.MenuCategory;
import com.example.food_app.models.Restaurant;
import com.example.food_app.repository.MenuCategoryRepository;
import com.example.food_app.repository.RestaurantRepository;

import com.example.food_app.services.MenuCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuCategoryServiceImpl(MenuCategoryRepository menuCategoryRepository,RestaurantRepository restaurantRepository) {
        this.menuCategoryRepository = menuCategoryRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public MenuCategoryResponse createCategory(MenuCategoryRequest request, UUID restaurantId, String ownerEmail) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new ResourceNotFoundException("restaurant not found with id :"+restaurantId));
        if (!restaurant.getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("you don't have the permission to add a new category");
        }
        MenuCategory menuCategory = MenuCategoryMapper.toEntity(request,restaurant);
        MenuCategory saved = menuCategoryRepository.save(menuCategory);
        return MenuCategoryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public MenuCategoryResponse updateCategory(UUID categoryId, UpdateMenuCategoryRequest request, String ownerEmail) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with id :"+categoryId));
        if (!menuCategory.getRestaurant().getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("you don't have the permission to update the category");
        }
        MenuCategoryMapper.updateEntity(request,menuCategory);
        MenuCategory saved = menuCategoryRepository.save(menuCategory);
        return MenuCategoryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteMenuCategory(UUID categoryId, String ownerEmail) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with id :"+categoryId));
        if (!menuCategory.getRestaurant().getOwner().getEmail().equals(ownerEmail)){
            throw new AccessDeniedException("you don't have the permission to delete ");
        }
        menuCategoryRepository.delete(menuCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuCategoryResponse> getAllMenuCategories(Pageable pageable) {
        return menuCategoryRepository.findAll(pageable).map(MenuCategoryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuCategoryResponse> getCategoriesByRestaurantId(UUID restaurantId, Pageable pageable) {
        return menuCategoryRepository.findByRestaurantId(restaurantId,pageable).map(MenuCategoryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuCategoryResponse> searchCategoriesByName(String name, Pageable pageable) {
        return menuCategoryRepository.findByNameIsContainingIgnoreCase(name, pageable).map(MenuCategoryMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuCategoryResponse getCategoryById(UUID categoryId) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with id :"+categoryId));
        return MenuCategoryMapper.toResponse(menuCategory);
    }
}
