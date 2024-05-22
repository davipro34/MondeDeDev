package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.services.ThemeService;

/**
 * The ThemeController class handles HTTP requests related to themes.
 */
@RestController
@RequestMapping("/themes")
public class ThemeController {
    
    private final ThemeService themeService;

    /**
     * Constructs a new ThemeController with the specified ThemeService.
     * 
     * @param themeService the ThemeService to be used by the controller
     */
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    /**
     * Retrieves a list of ThemeDTO objects representing all themes.
     * 
     * @return a list of ThemeDTO objects
     */
    @GetMapping
    public List<ThemeDTO> getThemes() {
        return themeService.getThemes();
    }
}
