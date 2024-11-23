package com.springboot.academic_system_with_security.security.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;


    public String createToken(Authentication authentication){
        Algorithm algorithm= Algorithm.HMAC256(secretKey);

        String username= authentication.getName();
        String authorities= authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        try {
            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(username)
                    .withClaim("authorities",authorities)
                    .withIssuedAt(Date.from(Instant.now()))
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException e) {
            throw new JWTCreationException("Error generating JWT Token",e);
        }
    }

    public Date getExpirationDate(){
        return Date.from(Instant.now().plus(45,ChronoUnit.MINUTES));
        //return LocalDateTime.now(ZoneId.of("America/Bogota")).plusMinutes(45).toInstant(ZoneOffset.UTC);
    }

    public DecodedJWT validateToken(String token){
        Algorithm algorithm= Algorithm.HMAC256(secretKey);
        try {
            JWTVerifier jwtVerifier= JWT.require(algorithm)
                    .withIssuer("auth")
                    .build();
            return jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new JWTCreationException("JWT Token Invalid,not authorized",e);
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    public Claim extractClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public Map<String,Claim> extractAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }

}