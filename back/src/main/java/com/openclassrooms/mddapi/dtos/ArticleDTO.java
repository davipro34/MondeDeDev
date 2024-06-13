package com.openclassrooms.mddapi.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an article data transfer object (DTO).
 * This class is used to transfer article data between different layers of the application.
 */
@Data
@Builder(toBuilder = true)
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private String username;
    private Long userId;
    private String themeTitle;
    private Long themeId;
    private List<Long> commentIds;
    private LocalDateTime created_at;

    public List<Long> getCommentIds() {
        if (commentIds == null) {
            commentIds = new ArrayList<>();
        }
        return commentIds;
    }
}