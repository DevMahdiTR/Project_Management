package com.example.pfe_project.controllers.auth;


import com.example.pfe_project.dto.auth.AuthResponseDto;
import com.example.pfe_project.dto.auth.LoginDto;
import com.example.pfe_project.dto.auth.RegisterDto;
import com.example.pfe_project.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {


    private final AuthService authService;


    public AuthController (AuthService authService)
    {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterDto registerDto)
    {
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginDto loginDto)
    {
        return authService.login(loginDto);
    }

}
