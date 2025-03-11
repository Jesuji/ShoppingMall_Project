package com.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<WishlistItem> wishlistItems = new ArrayList<>();

    public Wishlist(Member member) {
        this.member = member;
    }
}
