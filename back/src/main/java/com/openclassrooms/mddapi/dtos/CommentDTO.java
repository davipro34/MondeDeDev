package com.openclassrooms.mddapi.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CommentDTO {
    private Long id;
    private String content;
    private Long userId;
    private String username;
    private Long articleId;
}