package com.openclassrooms.mddapi.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserDTO {
    private Long id;
    private String username;
    private String email;
}
