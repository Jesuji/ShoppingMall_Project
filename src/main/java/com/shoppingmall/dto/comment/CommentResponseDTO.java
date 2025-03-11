package com.shoppingmall.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private String memberName;
    private String createdAt;
    private List<CommentResponseDTO> childComments; // 대댓글
}
