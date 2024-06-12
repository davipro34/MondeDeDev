package com.openclassrooms.mddapi.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Component
public class ArticleMapper {

    private UserRepository userRepository;
    private ThemeRepository themeRepository;
    private CommentRepository commentRepository;

    public ArticleMapper(UserRepository userRepository, ThemeRepository themeRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Converts an Article object to an ArticleDTO object.
     *
     * @param article the Article object to convert
     * @return the converted ArticleDTO object
     */
    public ArticleDTO toDTO(Article article) {
        if (article == null) {
            return null;
        }
        return ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .created_at(article.getCreated_at())
                .userId(article.getUser().getId())
                .themeId(article.getTheme().getId())
                .commentIds(article.getComments().stream().map(Comment::getId).collect(Collectors.toList()))
                .build();
    }

    /**
     * Converts an ArticleDTO object to an Article entity.
     *
     * @param articleDTO The ArticleDTO object to convert.
     * @return The converted Article entity.
     */
    public Article toEntity(ArticleDTO articleDTO) {
        if (articleDTO == null) {
            return null;
        }
    
        User user = userRepository.findById(articleDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id " + articleDTO.getUserId()));
    
        Theme theme = themeRepository.findById(articleDTO.getThemeId())
            .orElseThrow(() -> new RuntimeException("Theme not found with id " + articleDTO.getThemeId()));
    
        List<Comment> comments = articleDTO.getCommentIds().stream()
            .map(id -> commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found with id " + id)))
            .collect(Collectors.toList());
    
        return Article.builder()
            .id(articleDTO.getId())
            .title(articleDTO.getTitle())
            .content(articleDTO.getContent())
            .created_at(articleDTO.getCreated_at())
            .user(user)
            .theme(theme)
            .comments(comments)
            .build();
    }
}
