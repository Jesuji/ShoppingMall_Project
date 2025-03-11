package com.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //상품 이름
    private String description; //상품 상세 설명
    private int price; //상품 가격
    private int stock; //재고
    private String category; // 카테고리
    private String imagePath; //이미지 파일 경로

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Product(String name, String description, int price, int stock, String category, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(Long productId) {
        this.id = productId;
    }
}
