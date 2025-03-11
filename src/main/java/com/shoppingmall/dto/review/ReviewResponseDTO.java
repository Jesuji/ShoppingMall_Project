package com.shoppingmall.dto.review;

import com.shoppingmall.dto.comment.CommentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private String content;
    private int rating;
    private String memberName;
    private String createdAt;
    private List<CommentResponseDTO> comments = new ArrayList<>(); // 댓글 리스트
}
