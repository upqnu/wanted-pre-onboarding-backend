package wanted.freeonboarding.backend.wwrn.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Company;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.repository.CompanyRepository;

@RestController
@RequestMapping(produces = "application/json; charset=utf8")
@Slf4j
public class HomeController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public HomeController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @PostMapping("/login/company")
    public ResponseEntity<?> companyLogin(@RequestBody LoginRequest request) {
        try {
            // 회사용 로그인 처리
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 비밀번호를 해시화하여 저장된 비밀번호와 비교
            String encodedPassword = passwordEncoder.encode(request.getPassword());

            // 회사 정보 가져오기
            Company company = companyRepository.findByEmail(request.getUsername());

            if (company == null) {
                log.error("회사 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회사 정보를 찾을 수 없습니다.");
            } else if (!passwordEncoder.matches(request.getPassword(), company.getPassword())) {
                log.error("비밀번호가 일치하지 않습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
            } else {
                return ResponseEntity.ok(company);
            }
//            if (company == null) {
//                log.error("회사 정보를 찾을 수 없습니다.");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회사 정보를 찾을 수 없습니다.");
//            } else {
//                return ResponseEntity.ok(company);
//            }
        } catch (Exception e) {
            log.error("오류로 로그인 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("오류로 회사 로그인 실패");
        }
    }

    @PostMapping("/login/applicant")
    public ResponseEntity<?> applicantLogin(@RequestBody LoginRequest request) {
        try {
            // 지원자용 로그인 처리
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 비밀번호를 해시화하여 저장된 비밀번호와 비교
            String encodedPassword = passwordEncoder.encode(request.getPassword());

            // 지원자 정보 가져오기
            Applicant applicant = applicantRepository.findByEmail(request.getUsername());

            if (applicant == null) {
                log.error("지원자 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("지원자 정보를 찾을 수 없습니다.");
            } else if (!passwordEncoder.matches(request.getPassword(), applicant.getPassword())) {
                log.error("비밀번호가 일치하지 않습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
            } else {
                return ResponseEntity.ok(applicant);
            }
//            if (applicant == null) {
//                log.error("지원자 정보를 찾을 수 없습니다.");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("지원자 정보를 찾을 수 없습니다.");
//            } else if (!request.getPassword().equals(applicant.getPassword())) {
//                log.error("비밀번호가 일치하지 않습니다.");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
//            } else {
//                return ResponseEntity.ok(applicant);
//            }
        } catch (Exception e) {
            log.error("오류로 로그인 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("오류로 지원자 로그인 실패");
        }
    }

    @GetMapping("/home")
    public String home() {
        return "여기는 원티드 프리온보딩 백엔드 - 사전과제 [wanted wantU right now]의 메인 페이지!";
    }

    @GetMapping("/logout")
    public String logoutUrl() {
        return "로그아웃 되었!";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

}
