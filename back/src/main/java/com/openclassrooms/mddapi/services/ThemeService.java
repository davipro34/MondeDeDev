package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.mappers.ThemeMapper;
import com.openclassrooms.mddapi.repository.ThemeRepository;

/**
 * This class represents a service for managing themes.
 */
@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    /**
     * Constructs a new ThemeService with the specified repositories and mappers.
     *
     * @param themeRepository The repository for accessing theme data.
     * @param themeMapper The mapper for converting theme entities to DTOs.
     */
    public ThemeService(ThemeRepository themeRepository, ThemeMapper themeMapper) {
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    /**
     * Retrieves all themes and converts them to DTOs.
     *
     * @return A list of ThemeDTO objects representing the themes.
     */
    public List<ThemeDTO> getThemes() {
        return themeRepository.findAll().stream()
                .map(themeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
