package com.openclassrooms.mddapi.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private List<Long> themeIds;
}
