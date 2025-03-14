package com.shoppingmall.service;

import com.shoppingmall.dto.comment.CommentRequestDTO;
import com.shoppingmall.dto.comment.CommentResponseDTO;
import com.shoppingmall.dto.review.ReviewResponseDTO;
import com.shoppingmall.entity.Comment;
import com.shoppingmall.entity.Member;
import com.shoppingmall.entity.Review;
import com.shoppingmall.repository.CommentRepository;
import com.shoppingmall.repository.MemberRepository;
import com.shoppingmall.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void addComment(Long memberId, Long reviewId, CommentRequestDTO Dto, Long parentCommentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        // 부모 댓글 가져오기 (대댓글인 경우)
        Comment parentComment = null;
        if(parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = new Comment();
        comment.setMember(member);
        comment.setReview(review);
        comment.setContent(Dto.content());
        comment.setParentComment(parentComment);

        commentRepository.save(comment);
    }

    // 댓글을 계층적으로 가져오려면, 부모 댓글과 대댓글을 구분해서 데이터 반환
    public List<CommentResponseDTO> getCommentsByReview(Long reviewId) {
        List<Comment> comments = commentRepository.findByReviewId(reviewId);

        return comments.stream()
                .filter(comment -> comment.getParentComment() == null) //부모 댓글만 가져옴
                .map(parentComment -> new CommentResponseDTO(
                        parentComment.getId(),
                        parentComment.getContent(),
                        parentComment.getMember().getName(),
                        parentComment.getCreatedAt().format(formatter),
                        parentComment.getChildComments().stream()
                                .map(childComment -> new CommentResponseDTO(
                                        childComment.getId(),
                                        childComment.getContent(),
                                        childComment.getMember().getName(),
                                        childComment.getCreatedAt().format(formatter),
                                        null // 대댓글 자식은 없음
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    public List<ReviewResponseDTO> getReviewsWithComments(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (reviews.isEmpty()) {
            log.warn("No reviews found for product ID {}", productId);
        }

        return reviews.stream()
                .map(review -> {
                    // 각 리뷰에 대한 댓글 가져오기
                    List<CommentResponseDTO> comments = getCommentsByReview(review.getId());
                    return new ReviewResponseDTO(
                            review.getId(),
                            review.getContent(),
                            review.getRating(),
                            review.getMember().getName(),
                            review.getCreatedAt().toString(),
                            comments
                    );
                }).collect(Collectors.toList());
    }

    // 댓글 수정
    public void updateComment(Long commentId, String newContent, Member member) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if(!comment.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("본인만 댓글을 수정할 수 있습니다.");

        }
        comment.setContent(newContent);
        commentRepository.save(comment);
    }

    // 부모 댓글을 삭제하면, 대댓글도 같이 삭제되도록 Cascade 설정
    public void deleteComment(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if(!comment.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("본인만 댓글을 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}
