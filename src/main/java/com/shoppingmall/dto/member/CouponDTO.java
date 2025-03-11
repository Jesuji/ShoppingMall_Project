package com.shoppingmall.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
    private Long couponId;
    private String description;
    private int discountAmount;
    private boolean isUsed;
    private String expirationDate;
}
