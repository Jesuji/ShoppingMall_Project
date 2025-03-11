package com.shoppingmall.dto.order;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {

    @NotEmpty(message = "수령인 이름은 필수 항목입니다.")
    private String recipientName;

    private String phoneNumber;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private Long couponId;
    private int points;
    private String paymentMethod;
    private Boolean isDefault;
    private String deliveryRequest;

    private int totalAmount;
    private int finalAmount;

    private List<OrderItemDTO> selectedItems = new ArrayList<>(); // 선택한 상품 목록
}
