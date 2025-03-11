package com.shoppingmall.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/* 주문 완료하면 confirmation화면에서 보여줄 DTO*/
@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private String orderDate;
    private String deliveryRequest;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private ShippingStatus shippingStatus;
    private String recipientName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private int totalAmount;
    private int finalAmount; //쿠폰, 적립금 적용된 총 금액
    private List<OrderItemDTO> items;
}
