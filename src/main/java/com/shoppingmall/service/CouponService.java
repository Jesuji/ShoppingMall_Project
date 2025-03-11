package com.shoppingmall.service;

import com.shoppingmall.dto.member.CouponDTO;
import com.shoppingmall.entity.Coupon;
import com.shoppingmall.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public List<CouponDTO> getMemberCoupons(Long memberId) {
        List<Coupon> coupons = couponRepository.findByMemberId(memberId);

        return coupons.stream()
                .map(coupon -> new CouponDTO(
                        coupon.getId(),
                        coupon.getDescription(),
                        coupon.getCouponDiscount(),
                        coupon.getIsUsed(),
                        coupon.getExpirationDate()
                )).collect(Collectors.toList());
    }
}
