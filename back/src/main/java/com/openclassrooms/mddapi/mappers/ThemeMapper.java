package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.models.Theme;
import org.springframework.stereotype.Component;

@Component
public class ThemeMapper {

    /**
     * Converts a Theme entity to a ThemeDTO.
     *
     * @param theme The Theme entity to convert.
     * @return The converted ThemeDTO, or null if the input theme is null.
     */
    public ThemeDTO toDTO(Theme theme) {
        if (theme == null) {
            return null;
        }
        return ThemeDTO.builder()
                .id(theme.getId())
                .title(theme.getTitle())
                .description(theme.getDescription())
                .build();
    }

    /**
     * Converts a ThemeDTO to a Theme entity.
     *
     * @param themeDTO The ThemeDTO to convert.
     * @return The converted Theme entity, or null if the input themeDTO is null.
     */
    public Theme toEntity(ThemeDTO themeDTO) {
        if (themeDTO == null) {
            return null;
        }
        Theme theme = new Theme();
        theme.setId(themeDTO.getId());
        theme.setTitle(themeDTO.getTitle());
        theme.setDescription(themeDTO.getDescription());
        return theme;
    }
}
