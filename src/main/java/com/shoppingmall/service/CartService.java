package com.shoppingmall.service;

import com.shoppingmall.dto.cart.CartDTO;
import com.shoppingmall.dto.cart.CartItemDTO;
import com.shoppingmall.entity.Cart;
import com.shoppingmall.entity.CartItem;
import com.shoppingmall.entity.Member;
import com.shoppingmall.entity.Product;
import com.shoppingmall.repository.CartItemRepository;
import com.shoppingmall.repository.CartRepository;
import com.shoppingmall.repository.MemberRepository;
import com.shoppingmall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;

    public void addToCart(Long memberId, Long productId, int quantity) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        //memberId로 회원 장바구니 조회 or 생성
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    Cart newCart = new Cart(member);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid productId"));

        // 기존 장바구니 항목 확인
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if(existingItem != null) {
            // 이미 존재하는 항목이면 수량만 증가
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setTotalPrice(existingItem.getTotalPrice() + quantity * product.getPrice());
        } else {
            // 새로운 항목 추가
            CartItem cartItem = new CartItem(cart, product, quantity);
            cart.addCartItem(cartItem);
            cartItemRepository.save(cartItem);
        }

        cartRepository.save(cart);
    }

    public CartDTO getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));

        List<CartItemDTO> cartItemsDTO = cart.getCartItems().stream()
                .map(item -> new CartItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImagePath(),
                        item.getQuantity(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());

        return new CartDTO(cartItemsDTO, cart.calculateTotalPrice());
        }

        //장바구니가 비어있어도 에러 x
        public Cart getCartItemsByMemberId(Long memberId) {
            return cartRepository.findByMemberId(memberId)
                    .orElseGet(() -> {
                        Cart cart = new Cart();
                        cart.setMember(memberRepository.findById(memberId)
                                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다.")));
                            return cartRepository.save(cart);
                    });
        }

        public void deleteFromCart(Long memberId, Long cartItemId) {
            Cart cart = cartRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 회원의 위시 리스트를 찾을 수 없습니다."));

            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(cartItemId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("장바구니에 해당 상품이 존재하지 않습니다."));

            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
            cartRepository.save(cart);
        }
    }



