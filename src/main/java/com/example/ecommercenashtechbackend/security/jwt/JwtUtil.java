package com.example.ecommercenashtechbackend.security.jwt;

import com.example.ecommercenashtechbackend.exception.custom.ForbiddenException;
import com.example.ecommercenashtechbackend.security.UserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil implements Serializable {
    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    @Value("${JWT_EXPIRATION_TIME}")
    private long ACCESS_JWT_EXPIRATION;

    @Value("${JWT_REFRESH_EXPIRATION_TIME}")
    private long REFRESH_JWT_EXPIRATION;

    public String generateAccessToken(UserDetail userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(userDetails.getUser().getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String generateTokenValidWithin(UserDetail userDetails, int min){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + min * 60 * 1000);

        return Jwts.builder()
                .setSubject(userDetails.getUser().getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String generateRefreshToken(UserDetail userDetails){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(userDetails.getUser().getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUserNameFromJwtToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) throws Exception{
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ForbiddenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("JWT claims string is empty.");
        }
    }
}
