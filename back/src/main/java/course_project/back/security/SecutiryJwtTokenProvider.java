package course_project.back.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import java.util.Date;
import javax.crypto.SecretKey;

public class SecutiryJwtTokenProvider {
    private SecretKey key = Keys.hmacShaKeyFor("super_puper_secret_key!!!!!!!!!!!!!!!!!!!!!".getBytes());
    private String adminLogin = "admin";

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(System.currentTimeMillis() + 86400000);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .claim("authorities", authentication.getAuthorities())
                .signWith(key)
                .compact();

        System.out.println("generated token: " + token);
        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // public String getAuthoritiesFromJWT(String token) {
    //     Claims claims = Jwts.parserBuilder()
    //             .setSigningKey(key)
    //             .build()
    //             .parseClaimsJws(token)
    //             .getBody();
    //     return claims.getSubject();
    // }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public boolean isAdmin(String token) {
        return this.getUsernameFromJWT(token).equals(adminLogin); // or just super secured admin login
    }

    public String getAdminLogin() {
        return adminLogin;
    }
}
