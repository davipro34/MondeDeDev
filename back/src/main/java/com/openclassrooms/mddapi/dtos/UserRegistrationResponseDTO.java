package com.openclassrooms.mddapi.dtos;

import lombok.Data;

/**
 * Represents a user registration response data transfer object.
 */
@Data
public class UserRegistrationResponseDTO {
    private String username;
    private String email;
}
