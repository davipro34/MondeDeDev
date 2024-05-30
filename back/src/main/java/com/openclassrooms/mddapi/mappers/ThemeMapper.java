package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.models.Theme;

/**
 * The ThemeMapper interface is responsible for mapping Theme objects to ThemeDTO objects and vice versa.
 */
@Mapper(componentModel = "spring")
public interface ThemeMapper {

    /**
     * Converts a Theme object to a ThemeDTO object.
     *
     * @param theme The Theme object to be converted.
     * @return The corresponding ThemeDTO object.
     */
    ThemeDTO toDTO(Theme theme);

    /**
     * Converts a ThemeDTO object to a Theme object.
     *
     * @param themeDTO The ThemeDTO object to be converted.
     * @return The corresponding Theme object.
     */
    Theme toEntity(ThemeDTO themeDTO);
}
