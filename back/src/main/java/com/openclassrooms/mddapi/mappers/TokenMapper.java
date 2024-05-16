package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;

import com.openclassrooms.mddapi.dtos.TokenResponseDTO;

/**
 * This interface represents a mapper for converting tokens.
 */
@Mapper
public interface TokenMapper {
    /**
     * Converts a token to a token response DTO.
     *
     * @param token The token to be converted.
     * @return The converted token response DTO.
     */
    TokenResponseDTO tokenToTokenResponseDTO(String token);
}
