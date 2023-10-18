package wanted.freeonboarding.backend.wwrn.security_config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder; // BCryptPasswordEncoder를 사용

    @Autowired
    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info(String.valueOf(authentication));
        log.info(username + " : " + password);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (user != null && passwordMatches(password, user.getPassword())) {
            // 사용자의 권한 정보를 설정합니다.
            List<GrantedAuthority> authorities = new ArrayList<>();

            // 사용자가 가지는 권한을 여기에 추가합니다.
            // 예를 들어, "ROLE_USER" 또는 "ROLE_ADMIN"과 같은 권한을 사용자에게 부여할 수 있습니다.
            // authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            // authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            // 사용자의 권한을 UserDetails에서 가져와서 authorities에 추가할 수도 있습니다.
            authorities.addAll(user.getAuthorities());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
            log.info(String.valueOf(auth));
            return auth;
        } else {
            log.error("Authentication failed for user: " + username);
            throw new BadCredentialsException("Authentication failed for user: " + username);
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    // 비밀번호 일치 여부를 확인하는 메서드
    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}