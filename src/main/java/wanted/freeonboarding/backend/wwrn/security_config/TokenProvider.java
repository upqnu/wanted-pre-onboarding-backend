package wanted.freeonboarding.backend.wwrn.security_config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenProvider implements InitializingBean {

    // 사용자 권한 정보 (현재 사용자 권한은 세분화되어 있지 않음.)
    public static final String AUTH = "authorities"; // 토큰 claim에 사용자 권한을 입력할 key 값과 일치시켜준다

    // JWT 서명용 비밀키
    private final String SECRET;

    // 액세스 토큰 유효시간
    private final Long ACCESS_TOKEN_DURATION_MS;

    // 리프레시 토큰 유효시간
    private final Long REFRESH_TOKEN_DURATION_MS;

    // JWT 서명용 키
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("1800") Long accessTokenDuration,
            @Value("604800") Long refreshTokenDuration
    ) {
        this.SECRET = secret;
        this.ACCESS_TOKEN_DURATION_MS = accessTokenDuration * 1000;
        this.REFRESH_TOKEN_DURATION_MS = refreshTokenDuration * 1000;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] decodedKeyBytes = Decoders.BASE64.decode(SECRET);
        this.key = Keys.hmacShaKeyFor(decodedKeyBytes);
    }
}
