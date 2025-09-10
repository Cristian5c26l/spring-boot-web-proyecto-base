package com.ipn.mx.springbootwebceroaexperto.common.infrastructure.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "7A4F6B3951436E7458684C326C5279304568596D3452787543656A4A50667742";// 64 caracteres
    private static final long TOKEN_EXPIRATION = 1000 * 60 * 60 * 24; // ESTE TIEMPO ES 24 horas expresado en milisegundos
    private static final long RENEW_WINDOW = 1000 * 60 * 60 * 24 * 7; // Ventana de renovacion de token
    //private static final long TOKEN_EXPIRATION = 1000;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = Map.of("authorities", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
        );

        return generateToken(claims, userDetails.getUsername());
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token or mal formed", e);
        }
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsMapper) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsMapper.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public boolean canBeTokenRenewed(String token) {
        return getExpirationDateFromToken(token).before(new Date(System.currentTimeMillis() + RENEW_WINDOW));
    }

    public String renewToken(String token, UserDetails userDetails) {

        if (!canBeTokenRenewed(token)) {
            throw new RuntimeException("Token cannot be renewed");
        }

        return generateToken(userDetails);

    }
}
