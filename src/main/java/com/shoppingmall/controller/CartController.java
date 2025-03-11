package com.shoppingmall.controller;

import com.shoppingmall.dto.cart.CartDTO;
import com.shoppingmall.dto.cart.CartItemRequestDTO;
import com.shoppingmall.entity.Cart;
import com.shoppingmall.entity.Member;
import com.shoppingmall.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //장바구니 추가
    @PostMapping("/add/cart")
    public ResponseEntity<String> addToCart(@RequestBody CartItemRequestDTO dto,
                                            HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if(member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        cartService.addToCart(member.getId(), dto.productId(), dto.quantity());

        return ResponseEntity.ok("장바구니에 상품이 추가되었습니다.");
    }

    //장바구니 조회
    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        // 로그인된 사용자가 아니면 로그인 페이지로 리다이렉트
        if (member == null) {
            return "redirect:/login";
        }
            Cart cart = cartService.getCartItemsByMemberId(member.getId());

        if(cart == null || cart.getCartItems().isEmpty()) { //장바구니가 비어있을 때
            model.addAttribute("cartItems", Collections.emptyList());
            model.addAttribute("totalPrice", 0);
        } else {
            CartDTO cartDTO = cartService.getCart(cart.getId());
            model.addAttribute("cartItems", cartDTO.cartItems());
            model.addAttribute("totalPrice", cartDTO.totalPrice());
        }
            return "cart";
    }

    //장바구니에서 삭제
    @DeleteMapping("/delete/cart/{productId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long productId, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        cartService.deleteFromCart(member.getId(), productId);

        return ResponseEntity.ok("장바구니에서 상품이 삭제되었습니다.");
    }
}
