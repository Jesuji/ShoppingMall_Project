package com.shoppingmall.controller;

import com.shoppingmall.dto.wishlist.WishlistDTO;
import com.shoppingmall.dto.wishlist.WishlistItemRequestDTO;
import com.shoppingmall.entity.Member;
import com.shoppingmall.entity.Wishlist;
import com.shoppingmall.service.WishlistService;
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
public class WishlistController {

    private final WishlistService wishlistService;

    //위시 리스트 추가
    @PostMapping("/add/wishlist")
    public ResponseEntity<String> addToWishlist(@RequestBody WishlistItemRequestDTO Dto,
                                                HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if(member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            wishlistService.addToWishlist(member.getId(), Dto.productId());
            return ResponseEntity.ok("위시리스트에 상품이 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            // 이미 추가된 상품일 경우 예외 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("위시리스트 추가 중 오류가 발생했습니다.");
        }
    }

    //위시 리스틑 조회
    @GetMapping("/wishlist")
    public String showWishlist(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        // 로그인 상태가 아니면 로그인 페이지로 다이렉트
        if(member == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

            Wishlist wishlist = wishlistService.getWishlistByMemberId(member.getId());

            if(wishlist == null || wishlist.getWishlistItems().isEmpty()) {
                model.addAttribute("wishlistItems", Collections.emptyList());
            } else {
                WishlistDTO wishlistDTO = wishlistService.getWishlist(wishlist.getId());
                model.addAttribute("wishlistItems", wishlistDTO.items());
            }
            return "wishlist";
    }

    //위시 리스트에서 상품 삭제
    @DeleteMapping("/delete/wishlist/{productId}")
    public ResponseEntity<String> deleteWishlist(@PathVariable Long productId, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        wishlistService.deleteFromWishlist(member.getId(), productId);

        return ResponseEntity.ok("위시 리스트에서 삭제되었습니다.");
    }
}
