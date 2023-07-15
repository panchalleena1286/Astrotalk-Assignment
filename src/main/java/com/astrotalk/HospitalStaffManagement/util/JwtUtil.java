package com.astrotalk.HospitalStaffManagement.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Base64;
import java.util.List;

@Component
public class JwtUtil {

    // Secret key for signing the JWT
    private static final String SECRET_KEY = "sRToEilreNy3F+Mi/R/dIw9NySvEF9Qce1pWk4d6qVE=";
    //Expiration time
    private static final long EXPIRATION_TIME = 3600000;

    public String generateToken(String username, List<String> roles) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        Key signingKey = generateKey();

        return "Bearer " + Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(signingKey,SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    private static Key generateKey() {
        return generateSecretKey();
    }

    private Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(generateKey()).build();
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }

    private static SecretKey generateSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(decodedKey, SignatureAlgorithm.HS256.getJcaName());
    }

    public boolean validateToken(String token, String username) {

        Claims claims = parseToken(token);
        return !claims.getExpiration().before(new Date()) && extractUsername(token).equalsIgnoreCase(username);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}