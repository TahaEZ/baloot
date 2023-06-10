package org.iespring1402.Baloot.models;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class AuthToken {
    private String token;

    public AuthToken(String keyString, String issuer) throws Exception {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, 1);
        Date nextDay = calendar.getTime();
        Key key = Keys.hmacShaKeyFor(keyString.getBytes());

        this.token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer(issuer)
                .setIssuedAt(today)
                .setExpiration(nextDay)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static void validateToken(String token, String keyString, String issuer) throws Exception {
        Key key = Keys.hmacShaKeyFor(keyString.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody();

        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("JWT has expired.");
        }

        if (!claims.getIssuer().equals(issuer)) {
            throw new RuntimeException("Invalid issuer claim.");
        }
    }

    public String getToken() {
        return token;
    }
}
