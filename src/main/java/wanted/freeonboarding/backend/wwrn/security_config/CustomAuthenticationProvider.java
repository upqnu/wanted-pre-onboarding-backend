package wanted.freeonboarding.backend.wwrn.security_config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Component
@Slf4j
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
        setUserDetailsService(customUserDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            // 원래의 인증 프로세스 실행
            Authentication auth = super.authenticate(authentication);
            log.info("auth : " + String.valueOf(auth));

            // 여기서 사용자의 권한 정보를 직접 추출
            List<String> authorities = new ArrayList<>();
            for (GrantedAuthority authority : auth.getAuthorities()) {
                authorities.add(authority.getAuthority());
            }
            log.info("authorities : " + String.valueOf(authorities));

            // 사용자의 권한 정보를 설정한 새로운 Authentication 객체 생성
            UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), auth.getAuthorities());
            log.info("authenticatedUser : " + String.valueOf(authenticatedUser));

            // SecurityContextHolder에 새로운 Authentication 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

            log.info("auth : " + String.valueOf(auth));
            return auth;
        } catch (AuthenticationException e) {
            // 인증 실패 시 예외 처리
            throw e;
        }
    }

//    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
//        setUserDetailsService(customUserDetailsService);
//
//        // 아래 라인을 추가하여 비밀번호 암호화 검사를 비활성화합니다.
////        setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        // 사용자 인증을 수행하고 Authentication 객체를 반환합니다.
//        return super.authenticate(authentication);
//    }
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        // 여기에서 사용자의 비밀번호를 추가로 확인할 수 있습니다.
//        // 기존의 비밀번호 확인 로직을 추가합니다.
//        super.additionalAuthenticationChecks(userDetails, authentication);
//    }
}
