package com.shoppingmall.controller;

import com.shoppingmall.entity.Product;
import com.shoppingmall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/* 카테고리 별 조회 컨트롤러 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final ProductRepository productRepository;

    @GetMapping("/top")
    public String showTopCategory(Model model) {
        List<Product> topProducts = productRepository.findByCategory("상의");
        model.addAttribute("products", topProducts);
        model.addAttribute("categoryName", "상의");
        return "category/top"; // top.html로 이동
    }

    @GetMapping("/bottom")
    public String showBottomCategory(Model model) {
        List<Product> bottomProducts = productRepository.findByCategory("하의");
        model.addAttribute("products", bottomProducts);
        model.addAttribute("categoryName", "하의");
        return "category/bottom";
    }

    @GetMapping("/outerwear")
    public String showOuterCategory(Model model) {
        List<Product> outerwearProducts = productRepository.findByCategory("아우터");
        model.addAttribute("products", outerwearProducts);
        model.addAttribute("categoryName", "아우터");
        return "category/outerwear";
    }

    @GetMapping("/shoes")
    public String showShoesCategory(Model model) {
        List<Product> shoesProducts = productRepository.findByCategory("신발");
        model.addAttribute("products", shoesProducts);
        model.addAttribute("categoryName", "신발");
        return "category/shoes";
    }

    @GetMapping("/bags")
    public String showBagsCategory(Model model) {
        List<Product> bagsProducts = productRepository.findByCategory("가방");
        model.addAttribute("products", bagsProducts);
        model.addAttribute("categoryName", "가방");
        return "category/bags";
    }
}
