package com.openclassrooms.mddapi.dtos;

import java.util.List;
import lombok.Data;

/**
 * Represents a user updated response data transfer object.
 */
@Data
public class UserUpdatedResponseDTO {
    private Long id;
    private String username;
    private String email;
    private List<Long> subscribedThemeIds;
}