package com.libraryquerypie.onlinelibrarysystem.jwt;

import com.libraryquerypie.onlinelibrarysystem.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    private final Key key;
    private final long expirationTime;

    public JwtUtils(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long expirationTime) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.expirationTime = expirationTime;
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("emailId", user.getEmailId());
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmailId(String token) {
        return parseClaims(token).get("emailId", String.class);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("JWT 형식이 잘못되었습니다.", e);
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 형식입니다.", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 비어있습니다.", e);
        }
        return false;
    }
}
