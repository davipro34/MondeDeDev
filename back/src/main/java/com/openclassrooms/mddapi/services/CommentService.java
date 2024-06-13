package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * This class represents a service for managing comments.
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentMapper commentMapper;

    /**
     * Constructs a new CommentService with the specified repositories and mapper.
     *
     * @param commentRepository  the comment repository
     * @param userRepository    the user repository
     * @param articleRepository the article repository
     * @param commentMapper     the comment mapper
     */
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Retrieves all comments for a given article ID.
     *
     * @param articleId the ID of the article
     * @return a list of CommentDTO objects representing the comments
     */
    public List<CommentDTO> getCommentsByArticleId(Long articleId) {
        List<Comment> comments = commentRepository.findByArticle_Id(articleId);
        return comments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new comment.
     *
     * @param commentDTO the CommentDTO object representing the comment to be created
     * @return the created CommentDTO object
     */
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO, userRepository, articleRepository);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDTO(savedComment);
    }
}