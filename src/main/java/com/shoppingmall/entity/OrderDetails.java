package com.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.*;

/*
    각 주문에 포함된 개별 상품 정보와 수량, 가격 등의 세부 정보를 저장
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @Column
    private int price; //개별 상품의 가격

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
