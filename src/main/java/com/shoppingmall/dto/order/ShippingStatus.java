package com.shoppingmall.dto.order;

public enum ShippingStatus {
    PROCESSING("상품 준비 중"),
    SHIPPED("배송 중"),
    DELIVERED("배송 완료");

    private final String description;

    ShippingStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
