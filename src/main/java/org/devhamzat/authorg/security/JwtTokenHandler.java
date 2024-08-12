//package org.devhamzat.authorg.security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Component
//public class JwtTokenHandler {
//    private static final Logger logger = LoggerFactory.getLogger(JwtTokenHandler.class);
//    @Value("${app.jwt-secret}")
//    private String jwtSecret;
//
//    @Value("${app-jwt-expiration-milliseconds}")
//    private long jwtExpirationDate;
//
//
//    public String generateToken(Authentication authentication) {
//        String username = authentication.getName();
//        Date currentDate = new Date();
//        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(expireDate)
//                .signWith(key())
//                .compact();
//    }
//
//    private SecretKey key() {
//        return Keys.hmacShaKeyFor(
//                Decoders.BASE64URL.decode(jwtSecret)
//        );
//    }
//
//    public String getUsername(String token) {
//        Claims claims = Jwts.parser().setSigningKey(key())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        String userName = claims.getSubject();
//        return userName;
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(key())
//                    .build()
//                    .parse(token);
//            return true;
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
//        }
//        return false;
//    }
//}
