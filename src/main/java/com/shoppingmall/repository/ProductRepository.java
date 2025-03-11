package com.shoppingmall.repository;

import com.shoppingmall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category); //카테고리별 조회 메서드
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
