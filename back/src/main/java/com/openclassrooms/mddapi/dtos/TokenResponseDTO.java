package com.openclassrooms.mddapi.dtos;

import lombok.Data;

/**
 * Represents a data transfer object for a token response.
 */
@Data
public class TokenResponseDTO {
    private String token;
}