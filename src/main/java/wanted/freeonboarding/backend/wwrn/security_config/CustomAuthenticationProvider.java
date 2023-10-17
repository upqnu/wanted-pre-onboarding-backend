package wanted.freeonboarding.backend.wwrn.security_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
        setUserDetailsService(customUserDetailsService);

        // 아래 라인을 추가하여 비밀번호 암호화 검사를 비활성화합니다.
        setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사용자 인증을 수행하고 Authentication 객체를 반환합니다.
        return super.authenticate(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 여기에서 사용자의 비밀번호를 추가로 확인할 수 있습니다.
        // 기존의 비밀번호 확인 로직을 추가합니다.
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
