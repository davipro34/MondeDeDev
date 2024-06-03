package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.models.Theme;
import org.springframework.stereotype.Component;

@Component
public class ThemeMapper {

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
