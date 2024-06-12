package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;

/**
 * The controller class for managing articles.
 */
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    /**
     * Constructs a new ArticleController with the specified ArticleService and UserService.
     *
     * @param articleService the ArticleService to use
     * @param userService the UserService to use
     */
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    /**
     * Retrieves a list of articles for the themes subscribed by the current user.
     *
     * @param authentication the authentication object representing the current user
     * @return a list of ArticleDTO objects
     */
    @GetMapping("/articles")
    public List<ArticleDTO> getArticlesForSubscribedThemes(Authentication authentication) {
        UserDTO userDTO = userService.getCurrentUser(authentication);
        List<Long> themeIds = userDTO.getSubscribedThemeIds();
        return articleService.getArticlesForSubscribedThemes(themeIds);
    }

    /**
     * Retrieves an article by its ID.
     *
     * @param id the ID of the article to retrieve
     * @return the ArticleDTO object representing the article
     */
    @GetMapping("/articles/{id}")
    public ArticleDTO getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    /**
     * Creates a new article.
     *
     * @param articleDTO the ArticleDTO object representing the article to create
     * @return the ArticleDTO object representing the created article
     */
    @PostMapping("/articles")
    public ArticleDTO createArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.createArticle(articleDTO);
    }
}