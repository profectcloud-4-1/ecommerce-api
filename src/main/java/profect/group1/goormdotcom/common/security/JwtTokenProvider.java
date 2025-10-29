package profect.group1.goormdotcom.common.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import profect.group1.goormdotcom.user.domain.enums.UserRole;

// JWT 토큰 생성/파싱: secret.yml에 만료기간 미설정 시 만료되지않음
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidityMs;

    public JwtTokenProvider(
            @Value("${secret.jwt.secret}") String secret,
            @Value("${secret.jwt.accessTokenValidityMs:-1}") long accessTokenValidityMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityMs = accessTokenValidityMs;
    }

    public String generateAccessToken(UUID userId, String roleCode) {
        Date now = new Date();

        UserRole role = UserRole.fromCode(roleCode);
        System.out.println("role::: " + role.name());

        var builder =Jwts.builder()
                .subject(userId.toString())
                .claim("role", role.name())
                .issuedAt(now)
                .signWith(secretKey);
        
        if (accessTokenValidityMs > 0) {
            Date exp = new Date(now.getTime() + accessTokenValidityMs);
            builder.expiration(exp);
        }

        return builder.compact();
                
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}


