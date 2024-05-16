package com.openclassrooms.mddapi.dtos;

import lombok.Data;

/**
 * Represents a data transfer object for user registration information.
 */
@Data
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
}
