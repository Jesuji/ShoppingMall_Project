package com.shoppingmall.repository;

import com.shoppingmall.entity.Coupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Coupon c WHERE c.isUsed = true")
    void deleteUsedCoupons();

    List<Coupon> findByMemberId(Long memberId);
}
