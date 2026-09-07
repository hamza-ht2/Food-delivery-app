package com.example.food_app.repository;

import com.example.food_app.models.User;
import com.example.food_app.models.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
    Page<User> findUserByRole(Role role, Pageable pageable);
    Optional<User> findUserByPhone(String phone);
}
