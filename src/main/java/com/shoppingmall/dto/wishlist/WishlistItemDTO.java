package com.shoppingmall.dto.wishlist;

import lombok.Getter;
import lombok.Setter;

/* Wishlist에 담긴 간단한 상품 정보 */

@Getter
@Setter
public class WishlistItemDTO {
    private Long productId;
    private String name;
    private String ImagePath;
    private int price;

    public WishlistItemDTO(Long productId, String name, String imagePath, int price) {
        this.productId = productId;
        this.name = name;
        this.ImagePath = imagePath;
        this.price = price;
    }
}
