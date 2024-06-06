package com.openclassrooms.mddapi.mappers;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dtos.TokenResponseDTO;

@Component
public class TokenMapper {

    /**
     * Converts a token string to a TokenResponseDTO.
     *
     * @param token The token string to convert.
     * @return The converted TokenResponseDTO, or null if the input token is null.
     */
    public TokenResponseDTO tokenToTokenResponseDTO(String token) {
        if (token == null) {
            return null;
        }
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setToken(token);
        return tokenResponseDTO;
    }
}