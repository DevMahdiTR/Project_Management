package com.example.pfe_project.security.jwt;

import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.repository.token.TokenRepository;
import com.example.pfe_project.security.utility.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || ! authHeader.startsWith("Bearer "))       {filterChain.doFilter(request,response);     return;}
        String jwt = authHeader.substring(7);

        if(!jwtService.validateToken(jwt))  {   filterChain.doFilter(request,response) ;    return;}

        String userEmail = jwtService.getUsernameFromJWT(jwt);

        if(userEmail == null ||  SecurityContextHolder.getContext().getAuthentication() != null) {filterChain.doFilter(request,response);return;}

        UserEntity userEntity = (UserEntity) this.customUserDetailsService.loadUserByUsername(userEmail);
        var isTokenValid = tokenRepository.findByToken(jwt).map(t -> !t.isExpired() && !t.isRevoked()) .orElse(false);

        if(!jwtService.isTokenValid(jwt, userEntity) ||  !isTokenValid) {filterChain.doFilter(request, response);return;}

        var authToken = new UsernamePasswordAuthenticationToken(userEntity,null,userEntity.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }

}
