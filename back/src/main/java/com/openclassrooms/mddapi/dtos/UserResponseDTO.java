package com.openclassrooms.mddapi.dtos;

import java.util.List;

import lombok.Data;

/**
 * Represents a user response data transfer object.
 */
@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private List<Long> subscribedThemeIds;
}
