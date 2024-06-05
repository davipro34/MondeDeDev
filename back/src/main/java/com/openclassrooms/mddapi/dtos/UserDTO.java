package com.openclassrooms.mddapi.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for user information.
 * This class is used to transfer user data between different layers of the application.
 */
@Data
@Builder(toBuilder = true)
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private List<Long> themeIds;
}
