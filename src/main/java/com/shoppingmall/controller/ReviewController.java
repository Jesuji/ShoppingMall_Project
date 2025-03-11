package com.shoppingmall.controller;

import com.shoppingmall.dto.comment.CommentResponseDTO;
import com.shoppingmall.dto.review.ReviewRequestDTO;
import com.shoppingmall.dto.review.UpdateReviewRequestDTO;
import com.shoppingmall.entity.Member;
import com.shoppingmall.service.CommentService;
import com.shoppingmall.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 생성
    @PostMapping("/add/review")
    public ResponseEntity<String> addReview(@RequestBody ReviewRequestDTO Dto, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        reviewService.addReview(member.getId(), Dto);
        return ResponseEntity.ok("리뷰가 등록되었습니다.");
    }

    //리뷰 수정
    @PatchMapping("/update/review/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable(name = "reviewId") Long id,
                                               @RequestBody UpdateReviewRequestDTO Dto,
                                               HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        reviewService.updateReview(member.getId(), id, Dto);
        return ResponseEntity.ok("리뷰가 수정되었습니다.");
    }

    //마이페이지에서 '나의 리뷰'에서 삭제 가능
    @DeleteMapping("/delete/review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable(name = "reviewId") Long id,
                                               HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        reviewService.deleteReview(member.getId(), id);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
