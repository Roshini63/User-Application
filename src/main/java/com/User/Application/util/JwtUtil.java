package com.User.Application.util;

import org.springframework.stereotype.Component;
import java.util.*;
import io.jsonwebtoken.*;

@Component
public class JwtUtil {
    private final String SECRET = "mysecretkey";

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}