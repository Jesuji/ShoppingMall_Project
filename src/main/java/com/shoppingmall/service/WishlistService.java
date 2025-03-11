package com.shoppingmall.service;

import com.shoppingmall.dto.wishlist.WishlistDTO;
import com.shoppingmall.dto.wishlist.WishlistItemDTO;
import com.shoppingmall.entity.Member;
import com.shoppingmall.entity.Product;
import com.shoppingmall.entity.Wishlist;
import com.shoppingmall.entity.WishlistItem;
import com.shoppingmall.repository.MemberRepository;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.repository.WishlistItemRepository;
import com.shoppingmall.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public void addToWishlist(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        Wishlist wishlist = wishlistRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist(member);
                    return wishlistRepository.save(newWishlist);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        // 기존 위시리스트 항목 확인
        WishlistItem existingItem = wishlist.getWishlistItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if(existingItem != null) {
            // 이미 존재하는 항목이면
            throw new IllegalArgumentException("이미 위시리스트에 담긴 상품입니다.");
        } else {
            //새로운 항목 추가
            WishlistItem newWishlistItem = new WishlistItem(wishlist, product);
            wishlist.getWishlistItems().add(newWishlistItem);
            wishlistItemRepository.save(newWishlistItem);
        }

        wishlistRepository.save(wishlist);
    }

    public WishlistDTO getWishlist(Long memberId) {
        Wishlist wishlist = wishlistRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("위시 리스트를 찾을 수 없습니다."));

        List<WishlistItemDTO> wishlistItemDTO = wishlist.getWishlistItems().stream()
                .map(item -> new WishlistItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImagePath(),
                        item.getProduct().getPrice()
                ))
                .collect(Collectors.toList());

        return new WishlistDTO(wishlistItemDTO);
    }

    //위시 리스트가 비어있어도 에러 x
    public Wishlist getWishlistByMemberId(Long memberId) {
        return wishlistRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    Wishlist wishlist = new Wishlist();
                    wishlist.setMember(memberRepository.findById(memberId)
                            .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다.")));
                    return wishlistRepository.save(wishlist);
                });
    }

    public void deleteFromWishlist(Long memberId, Long wishlistItemId) {
        Wishlist wishlist = wishlistRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 위시 리스트를 찾을수 없습니다."));

        WishlistItem wishlistItem = wishlist.getWishlistItems().stream()
                .filter(item -> item.getProduct().getId().equals(wishlistItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("위시 리스트에 해당 상품이 존재하지 않습니다."));

        wishlist.getWishlistItems().remove(wishlistItem);
        wishlistItemRepository.delete(wishlistItem);
        wishlistRepository.save(wishlist);
    }
}
