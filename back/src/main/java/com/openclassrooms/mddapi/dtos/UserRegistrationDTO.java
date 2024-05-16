package com.openclassrooms.mddapi.dtos;

import lombok.Data;

/**
 * Represents a data transfer object for user registration information.
 */
@Data
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
