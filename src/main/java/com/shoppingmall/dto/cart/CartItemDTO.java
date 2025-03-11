package com.shoppingmall.dto.cart;

import lombok.Getter;
import lombok.Setter;

/* 장바구니에 담긴 각 상품에 대한 정보 */

@Getter
@Setter
public class CartItemDTO {
    private Long productId;
    private String name;
    private String ImagePath;
    private int quantity;
    private int totalPrice;

    public CartItemDTO(Long productId, String name, String ImagePath, int quantity, int totalPrice) {
        this.productId = productId;
        this.name = name;
        this.ImagePath = ImagePath;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
