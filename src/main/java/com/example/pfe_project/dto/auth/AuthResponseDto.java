package com.example.pfe_project.dto.auth;

import com.example.pfe_project.dto.user.UserEntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private UserEntityDto userEntityDto;
    private String token;

}
