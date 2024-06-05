package com.openclassrooms.mddapi.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for theme information.
 * This class is used to transfer theme data between different layers of the application.
 */
@Data
@Builder(toBuilder = true)
public class ThemeDTO {
    private Long id;
    private String title;
    private String description;
}
