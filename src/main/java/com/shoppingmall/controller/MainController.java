package com.shoppingmall.controller;

import com.shoppingmall.entity.Product;
import com.shoppingmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    //메인 화면 조회
    @GetMapping("/main")
    public String showMainPage(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "4") int size,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               Model model) {
        Page<Product> products;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 검색어가 있을 경우 해당 키워드로 검색
            products = productService.searchProducts(keyword, PageRequest.of(page, size));
        } else {
            // 검색어가 없을 경우 전체 상품 표시
            products = productService.getProducts(PageRequest.of(page, size));
        }

        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("keyword", keyword); // 검색 키워드 유지
        return "main";
    }

    //고객 센터
    @GetMapping("/customer-service")
    public String ShowCsPage() {
        return "cs";
    }

    //이용 안내
    @GetMapping("/info")
    public String ShowInfoPage() {
        return "info";
    }
}
