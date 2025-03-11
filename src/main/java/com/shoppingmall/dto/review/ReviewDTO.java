package com.shoppingmall.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* my-page에서 볼 수 있는 나의 리뷰 DTO */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id; //리뷰 ID
    private String productName; //상품 이름
    private String imagePath; //상품 이미지
    private int rating; //평점
    private String content; //리뷰 내용
    private String createdAt;
}
