package com.shoppingmall.dto.review;

public record ReviewRequestDTO(Long productId, String content, int rating) {
}
