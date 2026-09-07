package com.example.food_app.repository;

import com.example.food_app.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByUserId(UUID id);
    List<Address> findByUserIdAndIsSavedForLaterTrue(UUID userId);
}
