package com.shoppingmall.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipientName; //수령인 이름

    @Column(nullable = false)
    private String phoneNumber; //수령인 전화번호

    @Column(nullable = false)
    private String postalCode; //우편번호

    @Column(nullable = false)
    private String addressLine1; //기본 주소

    private String addressLine2; //상세 주소
    private Boolean isDefault = false; //기본 배송지 여부
    private String deliveryRequest; //배송시 요청 사항

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "address", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Order> order = new ArrayList<>();
}
