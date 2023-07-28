package com.example.pfe_project.service.auth;


import com.example.pfe_project.dto.auth.AuthResponseDto;
import com.example.pfe_project.dto.auth.LoginDto;
import com.example.pfe_project.dto.auth.RegisterDto;
import com.example.pfe_project.dto.user.UserEntityDto;
import com.example.pfe_project.exceptions.ResourceNotFoundException;
import com.example.pfe_project.models.role.Role;
import com.example.pfe_project.models.Token.Token;
import com.example.pfe_project.models.Token.TokenType;
import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.repository.role.RoleRepository;
import com.example.pfe_project.repository.token.TokenRepository;
import com.example.pfe_project.repository.user.UserRepository;
import com.example.pfe_project.security.jwt.JWTService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;


    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository repository,
            PasswordEncoder passwordEncoder,
            JWTService jwtService,
            TokenRepository tokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<AuthResponseDto> register(@NotNull RegisterDto registerDto) {

        if (userRepository.isPhoneNumberRegistered(registerDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Sorry, that phone number is already taken. Please choose a different one.");
        }
        if (userRepository.isUsernameRegistered(registerDto.getUsername())) {
            throw new IllegalArgumentException("Sorry, that username is already taken. Please choose a different one.");
        }

        if (userRepository.isEmailRegistered(registerDto.getEmail())) {
            throw new IllegalArgumentException("Sorry, that email is already taken. Please choose a different one.");
        }

        Role role = roleRepository.fetchRoleByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found [USER]."));

        UserEntity user = new UserEntity();

        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singletonList(role));

        UserEntity savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        AuthResponseDto authResponseDto = AuthResponseDto.builder()
                .token(jwtToken)
                .userEntityDto(new UserEntityDto(user))
                .build();
        return new ResponseEntity<AuthResponseDto>(authResponseDto,HttpStatus.OK);
    }

    public ResponseEntity<AuthResponseDto> login(@NotNull LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.fetchUserWithEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("The email address specified was not found in our system."));

        String jwtToken = generateAndSaveToken(user);

        AuthResponseDto authResponseDto = AuthResponseDto.builder()
                .token(jwtToken)
                .userEntityDto(new UserEntityDto(user))
                .build();

        return ResponseEntity.ok(authResponseDto);
    }

    private String generateAndSaveToken(UserEntity user) {
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return jwtToken;
    }

    private void saveUserToken(@NotNull UserEntity userEntity, @NotNull String jwtToken) {
        var token = Token.builder()
                .userEntity(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(@NotNull UserEntity userEntity) {
        var validUserTokens = tokenRepository.fetchAllValidTokenByUser(userEntity.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
