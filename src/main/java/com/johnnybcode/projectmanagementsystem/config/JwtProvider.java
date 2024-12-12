package com.johnnybcode.projectmanagementsystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Forma antigua:
//    public static String generateToken(Authentication auth) {
//       return Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 86400000))
//               .claim("email", auth.getName())
//               .signWith(key)
//               .compact();
//    }

    public static String generateToken(Authentication auth) {
        // Tiempo actual y expiración en 24 horas
        Instant now = Instant.now();
        Instant expiry = now.plus(24, ChronoUnit.HOURS);

        // Crear los claims de manera moderna
        Map<String, Object> claims = Map.of(
                "email", auth.getName(), // auth.getName() devuelve el email o nombre de usuario
                "authorities", auth.getAuthorities().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","))
        );

        // Construir el token con JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .addClaims(claims)
                .signWith(key); // Firmar con la clave secreta

        return jwtBuilder.compact();
    }

    public static String getEmailFromToken(String jwt) {

        jwt = jwt.substring(7);

        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

        return String.valueOf(claims.get("email"));
    }
}
