package com.shoppingmall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"member", "cartItems"})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

   @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    public Cart(Member member) {
        this.member = member;
    }

    //CartItem을 추가하는 메서드
    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    //장바구니의 총합을 계산하는 메서드
    public int calculateTotalPrice() {
        return cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
