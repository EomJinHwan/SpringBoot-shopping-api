package SpringBootShop.project.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    // SecretKey 객체 초기화
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access.expiration}") long accessTokenValidity,
            @Value("${jwt.refresh.expiration}") long refreshTokenValidity) {

        // Base64 디코딩 후 SecretKey 객체로 변환
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    // Access 토큰 생성
    public String createAccessToken(String userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                // Payload
                .subject(userId)    // 토큰 주인(주제) 설정
                .issuedAt(now)      // 발급 시간 설정
                .expiration(expiration) // 만료시간 설정
                // 서명
                .signWith(key)      // key로 서명
                // 완성
                .compact();
    }

    // 토큰 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 userId 꺼내오기
    public String getUserId(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
