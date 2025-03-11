package com.shoppingmall.service;

import com.shoppingmall.dto.review.ReviewDTO;
import com.shoppingmall.dto.review.ReviewRequestDTO;
import com.shoppingmall.dto.review.UpdateReviewRequestDTO;
import com.shoppingmall.entity.Member;
import com.shoppingmall.entity.Product;
import com.shoppingmall.entity.Review;
import com.shoppingmall.repository.MemberRepository;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public void addReview(Long memberId, ReviewRequestDTO Dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        Product product = productRepository.findById(Dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        Review review = new Review();
        review.setMember(member);
        review.setProduct(product);
        review.setContent(Dto.content());
        review.setRating(Dto.rating());

        reviewRepository.save(review);
    }

    //마이 페이지에서 나의 리뷰 조회 메서드
    public List<ReviewDTO> MyPageReviews(Long memberId) {
        List<Review> reviews = reviewRepository.findByMemberId(memberId);

        return reviews.stream()
                .map(review -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    return new ReviewDTO(
                            review.getId(),
                            review.getProduct().getName(),
                            review.getProduct().getImagePath(),
                            review.getRating(),
                            review.getContent(),
                            review.getCreatedAt().format(formatter)
                    );
                }).collect(Collectors.toList());
    }

    // my-page에서 리뷰 수정 메서드
    public void updateReview(Long memberId, Long reviewId, UpdateReviewRequestDTO Dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (!review.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        review.setContent(Dto.getContent());
        review.setRating(Dto.getRating());

        reviewRepository.save(review);
    }

    // 리뷰 삭제 메서드
    @Transactional
    public void deleteReview(Long memberId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        if (!review.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        reviewRepository.delete(review);
    }
}
