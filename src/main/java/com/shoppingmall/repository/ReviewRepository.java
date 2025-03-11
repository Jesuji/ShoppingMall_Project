package com.shoppingmall.repository;

import com.shoppingmall.entity.Comment;
import com.shoppingmall.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMemberId(Long memberId);
    List<Review> findByProductId(Long productId);
}
