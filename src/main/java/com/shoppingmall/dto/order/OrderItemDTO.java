package com.shoppingmall.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private String ImagePath;
    private int quantity;
    private int price;
}
