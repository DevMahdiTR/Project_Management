package com.example.pfe_project.security.config;


import com.example.pfe_project.security.utility.CustomUserDetailsService;
import com.example.pfe_project.security.jwt.JWTAuthenticationFilter;
import com.example.pfe_project.security.jwt.JwtAuthEntryPoint;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint authEntryPoint;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig (CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint)
    {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
    }


    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws  Exception
    {
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/api/v1/training-sessions/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/training-sessions/all/**").hasAnyAuthority("ADMIN","USER")
                .requestMatchers("/api/v1/holiday_type/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/holiday_type/all/**").hasAnyAuthority("ADMIN","USER")
                .requestMatchers("/api/v1/holiday_request/all/**").hasAnyAuthority("ADMIN","USER")
                .requestMatchers("/api/v1/holiday_request/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/role/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/feed_back_user/all/**").hasAnyAuthority("ADMIN","USER")
                .requestMatchers("/api/v1/feed_back_user/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/feed_back/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/feed_back/all/**").hasAnyAuthority("ADMIN","USER")
                .requestMatchers("/api/v1/work_position/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration configuration = new CorsConfiguration();
       configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
       configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
       configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
       configuration.setAllowCredentials(true);

       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", configuration);
       return source;
   }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(@NotNull AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter()
    {
        return new JWTAuthenticationFilter();
    }
}
