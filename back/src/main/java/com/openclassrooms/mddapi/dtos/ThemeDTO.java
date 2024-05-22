package com.openclassrooms.mddapi.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ThemeDTO {
    private Long id;
    private String title;
    private String description;
}
