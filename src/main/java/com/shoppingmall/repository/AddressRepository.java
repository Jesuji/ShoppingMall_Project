package com.shoppingmall.repository;

import com.shoppingmall.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByMemberIdAndIsDefaultTrue(Long memberId);

    // 기본 배송지 초기화
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.member.id = :memberId AND a.isDefault = true")
    void resetDefaultAddress(Long memberId);
}
