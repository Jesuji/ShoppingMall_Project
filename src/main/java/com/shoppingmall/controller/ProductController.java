package com.shoppingmall.controller;

import com.shoppingmall.dto.review.ReviewResponseDTO;
import com.shoppingmall.entity.Product;
import com.shoppingmall.service.CommentService;
import com.shoppingmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final CommentService commentService;

    //상품 상세 페이지
    @GetMapping("/products/{id}")
    public String showProductDetail(@PathVariable Long id,
                                    Model model) {
        Product product = productService.getProductById(id);

        if(product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        List<ReviewResponseDTO> reviewsWithComments = commentService.getReviewsWithComments(id);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviewsWithComments);

        return "product-detail";
    }
}
