package com.example.food_app.controllers;

import com.example.food_app.dto.restaurant.MenuItemRequest;
import com.example.food_app.dto.restaurant.MenuItemResponse;
import com.example.food_app.dto.restaurant.UpdateMenuItemRequest;

import com.example.food_app.services.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/menu-items")
public class MenuItemController {
    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/category/{categoryId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<MenuItemResponse> createMenuItem(@PathVariable UUID categoryId, @Valid @RequestBody MenuItemRequest request, @AuthenticationPrincipal UserDetails userDetails){
        MenuItemResponse created = menuItemService.createMenuItem(request,categoryId,userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable UUID id , @Valid @RequestBody UpdateMenuItemRequest request, @AuthenticationPrincipal UserDetails userDetails){
        MenuItemResponse updated = menuItemService.updateMenuItem(id, request, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("/{id}/toggle-availability")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<MenuItemResponse> toggleItemAvailability(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(menuItemService.toggleAvailability(id, userDetails.getUsername()));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable UUID id , @AuthenticationPrincipal UserDetails userDetails){
        menuItemService.deleteMenuItem(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getItemById(@PathVariable UUID id){
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<MenuItemResponse>> getItemByCategoryId(@PathVariable UUID categoryId, Pageable pageable){
        return ResponseEntity.ok(menuItemService.getItemsByMenuCategoryId(categoryId,pageable));
    }
    @GetMapping("/category/{categoryId}/available")
    public ResponseEntity<Page<MenuItemResponse>> getAvailableItemsByCategoryId(@PathVariable UUID categoryId, Pageable pageable){
        return ResponseEntity.ok(menuItemService.getAvailableItemsByCategoryId(categoryId, pageable));
    }
    @GetMapping("/search")
    public ResponseEntity<Page<MenuItemResponse>> searchItemByName(@RequestParam String name, Pageable pageable){
        return ResponseEntity.ok(menuItemService.getItemsByName(name, pageable));
    }
    @GetMapping("/price-range")
    public ResponseEntity<Page<MenuItemResponse>> getItemsByPriceRange(@RequestParam BigDecimal min , @RequestParam BigDecimal max , Pageable pageable){
        return ResponseEntity.ok(menuItemService.getItemsByPriceRange(min, max, pageable));
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<MenuItemResponse>> getAllItems(Pageable pageable) {
        return ResponseEntity.ok(menuItemService.getAllItems(pageable));
    }

}
