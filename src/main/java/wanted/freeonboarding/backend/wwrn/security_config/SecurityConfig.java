package wanted.freeonboarding.backend.wwrn.security_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.repository.CompanyRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private CompanyRepository companyRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(applicantRepository, companyRepository/*, passwordEncoder*/);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* csrf disabled */
                .csrf(AbstractHttpConfigurer::disable)

                /* h2-console 화면을 사용하기 위해 해당 옵션들을 disabled */
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )

                /* 요청별 권한 설정 */
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/logout")).permitAll()
                                .anyRequest().permitAll()
                )

                /* 로그인 설정 */
//                .httpBasic(withDefaults())
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // 로그인 페이지 경로
                                .loginProcessingUrl("/perform_login") // 로그인 처리를 위한 경로
                                .usernameParameter("username") // 로그인 폼에서 사용자 이름 입력 필드의 name 속성
                                .passwordParameter("password") // 로그인 폼에서 비밀번호 입력 필드의 name 속성
//                                .successHandler(yourSuccessHandler) // 로그인 성공 시의 핸들러
//                                .failureHandler(yourFailureHandler) // 로그인 실패 시의 핸들러
                )

                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                /* 로그아웃 설정 */
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/home")
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제 (선택 사항)
                );

        return http.build();
    }

}
