package com.openclassrooms.mddapi.dtos;

import lombok.Data;

/**
 * Data Transfer Object for user login information.
 * This class contains the necessary fields for user authentication.
 */
@Data
public class LoginDTO {
    private String usernameOrEmail;
    private String password;
}
