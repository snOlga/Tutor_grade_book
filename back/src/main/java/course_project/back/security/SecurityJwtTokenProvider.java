package course_project.back.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import java.util.Date;
import javax.crypto.SecretKey;

public class SecurityJwtTokenProvider {
    private SecretKey accessKey = Keys.hmacShaKeyFor("super_puper_secret_key!!!!!!!!!!!!!!!!!!!!!".getBytes());
    private SecretKey refreshKey = Keys.hmacShaKeyFor("super_puper_secret_refresh_key!!!!!!!!!!!!!!!!!!!!!".getBytes());

    public String generateAccessToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(System.currentTimeMillis() + 15 * 60 * 1000);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .claim("authorities", authentication.getAuthorities())
                .signWith(accessKey)
                .compact();

        System.out.println("generated access token: " + token);
        return token;
    }

    public String generateRefreshToken(String userEmail) {
        Date currentDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);

        String token = Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(refreshKey)
                .compact();

        System.out.println("generated refresh token: " + token);
        return token;
    }

    public String getUserEmailFromRefresh(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(refreshKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
