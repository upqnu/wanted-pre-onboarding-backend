package wanted.freeonboarding.backend.wwrn.security_config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
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

                /* 요청별 권한설정 */
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .anyRequest().permitAll()
                );

        return http.build();
    }
}
