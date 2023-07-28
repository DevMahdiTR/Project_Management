package com.example.pfe_project.security.jwt;

import com.example.pfe_project.models.user.UserEntity;
import com.example.pfe_project.security.utility.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Component
public class JWTService {

    public String generateToken(@NotNull UserEntity userEntity) {
        String email = userEntity.getEmail();
        Date currentDate = new Date();
        Date expireDate = new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION);

        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            if (isTokenExpired(token)) {
                throw new ExpiredJwtException(null, claims, "Token has expired");
            }

        } catch (ExpiredJwtException e) {
            System.out.print(e.getMessage());;
        }
        return true;
    }

    public <T> T extractClaim(String token, @NotNull Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, @NotNull UserEntity userDetails) {
        final String email = getUsernameFromJWT(token);
        return (email.equals(userDetails.getEmail())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String getUsernameFromJWT(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private @NotNull Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
