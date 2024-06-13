package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.services.CommentService;


/**
 * The CommentController class handles HTTP requests related to comments on articles.
 */
@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * Constructs a new CommentController with the specified CommentService.
     *
     * @param commentService the CommentService to be used by the controller
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Retrieves the comments for a specific article.
     *
     * @param articleId the ID of the article
     * @return a ResponseEntity containing a list of CommentDTO objects
     */
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentDTO> comments = commentService.getCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Creates a new comment for an article.
     *
     * @param commentDTO the CommentDTO object representing the comment to be created
     * @return a ResponseEntity containing the created CommentDTO object
     */
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(createdComment);
    }
}