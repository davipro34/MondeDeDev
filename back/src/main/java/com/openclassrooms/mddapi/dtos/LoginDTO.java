package com.openclassrooms.mddapi.dtos;

import lombok.Data;

/**
 * Represents a data transfer object for user login
 */
@Data
public class LoginDTO {
    private String email;
    private String password;
}
