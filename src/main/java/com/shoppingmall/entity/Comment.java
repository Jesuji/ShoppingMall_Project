package com.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // 부모 댓글 참조 -> 이 필드가 null이면 일반 댓글, null이 아니면 대댓글
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    // 대댓글 리스트
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> childComments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
