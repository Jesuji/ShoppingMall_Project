package com.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.*;

/* Member, Coupon 간의 조인 테이블 */

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isUsed = false;
    private int couponDiscount; // 할인 금액 또는 비율
    private String expirationDate; // 만료 날짜
    private String description; // 쿠폰 설명

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
