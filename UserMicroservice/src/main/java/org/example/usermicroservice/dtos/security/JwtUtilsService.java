package org.example.usermicroservice.dtos.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.example.usermicroservice.config.AppProperties;
import org.example.usermicroservice.details.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtUtilsService {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtilsService.class);
    private final AppProperties appProperties;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrinicipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrinicipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + appProperties.getJwtExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, appProperties.getJwtSecret())
                .compact();
    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(appProperties.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e){
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            logger.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String jwt){
        return Jwts.parser().setSigningKey(appProperties.getJwtSecret()).parseClaimsJws(jwt).getBody().getSubject();
    }
}
