package com.example.food_app.controllers;

import com.example.food_app.dto.restaurant.MenuCategoryRequest;
import com.example.food_app.dto.restaurant.MenuCategoryResponse;


import com.example.food_app.dto.restaurant.UpdateMenuCategoryRequest;
import com.example.food_app.services.MenuCategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/menu-categories")
public class MenuCategoryController {
    private final MenuCategoryService menuCategoryService;

    public MenuCategoryController(MenuCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }

    @PostMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<MenuCategoryResponse> createCategory(@PathVariable UUID restaurantId, @Valid @RequestBody MenuCategoryRequest request, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(menuCategoryService.createCategory(request,restaurantId, userDetails.getUsername()));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<MenuCategoryResponse> updateCategory(@PathVariable UUID id, @Valid @RequestBody UpdateMenuCategoryRequest request, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(menuCategoryService.updateCategory(id,request, userDetails.getUsername()));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public ResponseEntity<Void> deleteMenuCategory(@PathVariable UUID id , @AuthenticationPrincipal UserDetails userDetails){
        menuCategoryService.deleteMenuCategory(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<MenuCategoryResponse> getMenuCategoryById(@PathVariable UUID id){
        return ResponseEntity.ok(menuCategoryService.getCategoryById(id));
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Page<MenuCategoryResponse>> getMenuCategoryByRestaurantId(@PathVariable UUID restaurantId , Pageable pageable){
        return ResponseEntity.ok(menuCategoryService.getCategoriesByRestaurantId(restaurantId, pageable));
    }
    @GetMapping("/search")
    public ResponseEntity<Page<MenuCategoryResponse>> searchCategoriesByName(@RequestParam String name, Pageable pageable){
        return ResponseEntity.ok(menuCategoryService.searchCategoriesByName(name, pageable));
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<MenuCategoryResponse>> getAllCategories(Pageable pageable){
        return ResponseEntity.ok(menuCategoryService.getAllMenuCategories(pageable));
    }



}
