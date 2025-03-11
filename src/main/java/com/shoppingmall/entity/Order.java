package com.shoppingmall.entity;

import com.shoppingmall.dto.order.PaymentStatus;
import com.shoppingmall.dto.order.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod; // 결제 방식 (예: "신용카드", "계좌 이체")
    private String orderStatus; // 주문 상태 (예: "주문완료", "환불")
    private int totalAmount; //총 상품 금액
    private int finalAmount; //할인 적용된 최종 금액

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now(); // 결제 완료 시각

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address; //배송지 정보

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetailsList = new ArrayList<>();
}
