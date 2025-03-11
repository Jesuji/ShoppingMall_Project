package com.shoppingmall.dto.order;

public enum PaymentStatus {
    PENDING("결제 대기"),
    COMPLETED("결제 완료"),
    REFUNDED("환불");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
