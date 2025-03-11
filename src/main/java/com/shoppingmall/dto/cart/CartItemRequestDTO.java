package com.shoppingmall.dto.cart;

//프론트에서 전송되는 데이터를 수신하는 DTO
public record CartItemRequestDTO(Long productId, int quantity) {
}
