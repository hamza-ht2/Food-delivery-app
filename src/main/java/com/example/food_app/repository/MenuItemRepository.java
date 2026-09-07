package com.example.food_app.repository;

import com.example.food_app.models.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    Page<MenuItem> findByMenuCategoryId(UUID menuCategoryId, Pageable pageable);
    Page<MenuItem> findByMenuCategoryIdAndIsAvailableTrue(UUID menuCategoryId, Pageable pageable);
    Page<MenuItem> findByPriceBetween(BigDecimal min , BigDecimal max , Pageable pageable);
}
