package com.shoppingmall.dto.cart;

import java.util.List;

//장바구니에 담길 때 필요한 Product 정보 DTO
public record CartDTO(List<CartItemDTO> cartItems, int totalPrice) {
}
