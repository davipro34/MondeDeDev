package com.openclassrooms.mddapi.mappers;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dtos.TokenResponseDTO;

@Component
public class TokenMapper {

    public TokenResponseDTO tokenToTokenResponseDTO(String token) {
        if (token == null) {
            return null;
        }
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setToken(token);
        return tokenResponseDTO;
    }
}