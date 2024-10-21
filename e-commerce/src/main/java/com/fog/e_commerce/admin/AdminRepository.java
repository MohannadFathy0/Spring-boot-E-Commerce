package com.fog.e_commerce.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findById(Long id);
    Optional<Admin> findByEmail(String email);
}
