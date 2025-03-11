package com.shoppingmall.controller;

import com.shoppingmall.dto.comment.CommentRequestDTO;
import com.shoppingmall.dto.comment.CommentResponseDTO;
import com.shoppingmall.dto.comment.CommentUpdateRequest;
import com.shoppingmall.entity.Member;
import com.shoppingmall.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //부모 댓글 작성
    @PostMapping("/add/comments/{reviewId}")
    public ResponseEntity<String> addComment(@RequestBody CommentRequestDTO Dto,
                                             @PathVariable Long reviewId,
                                             HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        //parentCommentId는 null
        commentService.addComment(member.getId(), reviewId, Dto, null);
        return ResponseEntity.ok("댓글이 작성되었습니다.");
    }

    //대댓글 작성
    @PostMapping("/add/reply/{reviewId}/{parentCommentId}")
    public ResponseEntity<String> addReply(@RequestBody CommentRequestDTO Dto,
                                           @PathVariable Long reviewId,
                                           @PathVariable Long parentCommentId,
                                           HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인인 필요합니다.");
        }

        commentService.addComment(member.getId(), reviewId, Dto, parentCommentId);
        return ResponseEntity.ok("대댓글이 작성되었습니다.");
    }

    // 리뷰에 포함된 댓글 조회
    @GetMapping("/review/comments/{reviewId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByReview(@PathVariable Long reviewId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByReview(reviewId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PatchMapping("/products/update/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        try {
            commentService.updateComment(commentId, request.content());
            return ResponseEntity.ok("댓글이 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 댓글 삭제
    @DeleteMapping("/products/delete/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
