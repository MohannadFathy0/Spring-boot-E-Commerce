package com.fog.e_commerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long Id);
    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE " +
            "(:gender IS NULL OR p.gender = :gender) AND " +
            "(:category IS NULL OR p.category = :category)")
    Page<Product> findAllByFilters(
            @Param("gender") String gender,
            @Param("category") String category,
            Pageable pageable);
}
