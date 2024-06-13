package com.openclassrooms.mddapi.mappers;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * The CommentMapper class is responsible for mapping Comment objects to CommentDTO objects and vice versa.
 */
@Component
public class CommentMapper {

    /**
     * Converts a Comment object to a CommentDTO object.
     *
     * @param comment the Comment object to be converted
     * @return the corresponding CommentDTO object
     */
    public CommentDTO toDTO(Comment comment) {
        if (comment == null) {
            return null;
        }
        return CommentDTO.builder()
            .id(comment.getId())
            .content(comment.getContent())
            .userId(comment.getUser().getId())
            .username(comment.getUser().getUsername())
            .articleId(comment.getArticle().getId())
            .build();
    }

    /**
     * Converts a CommentDTO object to a Comment object.
     *
     * @param commentDTO the CommentDTO object to be converted
     * @param userRepository the UserRepository used to retrieve the User object
     * @param articleRepository the ArticleRepository used to retrieve the Article object
     * @return the corresponding Comment object
     */
    public Comment toEntity(CommentDTO commentDTO, UserRepository userRepository, ArticleRepository articleRepository) {
        if (commentDTO == null) {
            return null;
        }
        User user = userRepository.findById(commentDTO.getUserId()).orElse(null);
        Article article = articleRepository.findById(commentDTO.getArticleId()).orElse(null);

        return Comment.builder()
            .id(commentDTO.getId())
            .content(commentDTO.getContent())
            .user(user)
            .article(article)
            .build();
    }
}