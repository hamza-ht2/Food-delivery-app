package com.example.food_app.controllers;

import com.example.food_app.dto.restaurant.RestaurantRequest;
import com.example.food_app.dto.restaurant.RestaurantResponse;
import com.example.food_app.dto.restaurant.UpdateRestaurantRequest;
import com.example.food_app.services.RestaurantService;
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
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> getActiveRestaurants(Pageable pageable){
        return ResponseEntity.ok(restaurantService.getActiveRestaurants(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RestaurantResponse>> searchActiveRestaurant(@RequestParam String name, Pageable pageable){
        return ResponseEntity.ok(restaurantService.searchActiveRestaurantsByName(name, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurantRequest, userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable UUID id , @Valid @RequestBody UpdateRestaurantRequest restaurantRequest, @AuthenticationPrincipal UserDetails userDetails){
        RestaurantResponse updated = restaurantService.updateRestaurant(id,restaurantRequest, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/{id}/toggle-status")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public ResponseEntity<String> toggleActivity(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        restaurantService.toggleActiveStatus(id, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body("Activity Updated");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails){
        restaurantService.deleteRestaurant(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<RestaurantResponse>> getAllRestaurants(Pageable pageable){
        return ResponseEntity.ok(restaurantService.getAllRestaurants(pageable));
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public ResponseEntity<Page<RestaurantResponse>> getRestaurantsByOwner(@PathVariable UUID ownerId,Pageable pageable){
        return ResponseEntity.ok(restaurantService.getRestaurantsByOwnerId(ownerId, pageable));
    }

}
