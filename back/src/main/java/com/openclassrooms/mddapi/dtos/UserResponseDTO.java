package com.openclassrooms.mddapi.dtos;

import lombok.Data;

/**
 * Represents a user response data transfer object.
 */
@Data
public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
}
