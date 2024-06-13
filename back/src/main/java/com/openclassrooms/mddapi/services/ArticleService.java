package com.openclassrooms.mddapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
import com.openclassrooms.mddapi.mappers.ArticleMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * This class represents the service layer for managing articles.
 * It provides methods for retrieving, creating, and retrieving articles.
 */
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final ArticleMapper articleMapper;


    /**
     * Constructs a new ArticleService with the specified repositories and mapper.
     *
     * @param articleRepository The repository for managing articles.
     * @param userRepository The repository for managing users.
     * @param themeRepository The repository for managing themes.
     * @param articleMapper The mapper for converting between Article and ArticleDTO.
     */
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, ThemeRepository themeRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
        this.articleMapper = articleMapper;
    }
    
    /**
     * Retrieves a list of ArticleDTO objects for the subscribed themes with the given theme IDs.
     *
     * @param themeIds The IDs of the subscribed themes.
     * @return A list of ArticleDTO objects.
     * @throws EntityNotFoundException If a user or theme is not found.
     */
    public List<ArticleDTO> getArticlesForSubscribedThemes(List<Long> themeIds) {
        List<ArticleDTO> articles = new ArrayList<>();
        for (Long themeId : themeIds) {
            List<Article> themeArticles = articleRepository.findByTheme_Id(themeId);
            for (Article article : themeArticles) {
                User author = userRepository.findById(article.getUser().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found with id " + article.getUser().getId()));
                Theme theme = themeRepository.findById(themeId)
                        .orElseThrow(() -> new EntityNotFoundException("Theme not found with id " + themeId));
    
                ArticleDTO articleDTO = articleMapper.toDTO(article);
                articleDTO.setUsername(author.getUsername());
                articleDTO.setThemeTitle(theme.getTitle());
    
                articles.add(articleDTO);
            }
        }
        return articles;
    }

    /**
     * Creates a new article based on the provided ArticleDTO.
     *
     * @param articleDTO The ArticleDTO object representing the article to be created.
     * @return The created ArticleDTO object.
     * @throws EntityNotFoundException If the user or theme is not found.
     */
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        if (!userRepository.existsById(articleDTO.getUserId()) || !themeRepository.existsById(articleDTO.getThemeId())) {
            throw new EntityNotFoundException();
        }
    
        Article article = articleMapper.toEntity(articleDTO);
    
        User author = userRepository.findById(articleDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + articleDTO.getUserId()));
        Theme theme = themeRepository.findById(articleDTO.getThemeId())
                .orElseThrow(() -> new EntityNotFoundException("Theme not found with id " + articleDTO.getThemeId()));
    
        article.setUser(author);
        article.setTheme(theme);
        article.setComments(new ArrayList<>());
    
        Article savedArticle = articleRepository.save(article);
    
        ArticleDTO savedArticleDTO = articleMapper.toDTO(savedArticle);
        savedArticleDTO.setUsername(author.getUsername());
        savedArticleDTO.setThemeTitle(theme.getTitle());
        savedArticleDTO.setCommentIds(new ArrayList<>());
    
        return savedArticleDTO;
    }

    /**
     * Retrieves the ArticleDTO object with the specified ID.
     *
     * @param id The ID of the article to retrieve.
     * @return The ArticleDTO object.
     * @throws EntityNotFoundException If the article, user, or theme is not found.
     */
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id " + id));
    
        User author = userRepository.findById(article.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + article.getUser().getId()));
        Theme theme = themeRepository.findById(article.getTheme().getId())
                .orElseThrow(() -> new EntityNotFoundException("Theme not found with id " + article.getTheme().getId()));
    
        ArticleDTO articleDTO = articleMapper.toDTO(article);
        articleDTO.setUsername(author.getUsername());
        articleDTO.setThemeTitle(theme.getTitle());
    
        return articleDTO;
    }
}