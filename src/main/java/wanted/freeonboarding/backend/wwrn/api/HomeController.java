package wanted.freeonboarding.backend.wwrn.api;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Company;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.repository.CompanyRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = "application/json; charset=utf8")
@Slf4j
public class HomeController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public HomeController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

//    /* 회사의 로그인 */
//    @PostMapping("/login/company")
//    public ResponseEntity<?> companyLogin(@RequestBody LoginRequest request, HttpSession session) {
//        try {
//
//            // 회사 정보 가져오기
//            Company company = companyRepository.findByEmail(request.getUsername());
//
//            if (company == null) {
//                log.error("회사 정보를 찾을 수 없습니다.");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회사 정보를 찾을 수 없습니다.");
//            } else if (!passwordEncoder.matches(request.getPassword(), company.getPassword())) {
//                log.error("비밀번호가 일치하지 않습니다.");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
//            } else {
//                // 세션에 권한 정보 저장
//                session.setAttribute("userAuthorities", getAuthorities());
//                log.info("권한 : " + String.valueOf(getAuthorities()));
//                return ResponseEntity.ok(company);
//            }
//        } catch (Exception e) {
//            log.error("오류로 로그인 실패: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("오류로 회사 로그인 실패");
//        }
//    }
//
//    /* 지원자의 로그인 */
//    @PostMapping("/login/applicant")
//    public ResponseEntity<?> applicantLogin(@RequestBody LoginRequest request, HttpSession session) {
//        try {
//
//            // 지원자 정보 가져오기
//            Applicant applicant = applicantRepository.findByEmail(request.getUsername());
//
//            if (applicant == null) {
//                log.error("지원자 정보를 찾을 수 없습니다.");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("지원자 정보를 찾을 수 없습니다.");
//            } else if (!passwordEncoder.matches(request.getPassword(), applicant.getPassword())) {
//                log.error("비밀번호가 일치하지 않습니다.");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
//            } else {
//                // 세션에 권한 정보 저장
//                session.setAttribute("userAuthorities", getAuthorities());
//                log.info("권한 : " + String.valueOf(getAuthorities()));
//                return ResponseEntity.ok(applicant);
//            }
//        } catch (Exception e) {
//            log.error("오류로 로그인 실패: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("오류로 지원자 로그인 실패");
//        }
//    }
//
//    /* 로그인 직후, 회사 또는 지원자의 권한 정보를 가져오는 메서드 */
//    private List<String> getAuthorities() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            return authentication.getAuthorities().stream()
//                    .map(authority -> ((SimpleGrantedAuthority) authority).getAuthority())
//                    .collect(Collectors.toList());
//        }
//        return Collections.emptyList();
//    }

    /* 로그인(Basic Authenticatoin) */
    @GetMapping("/login")
    public String hello() {
        return "Hello!";
    }

    /* 로그아웃 */
    @GetMapping("/logout")
    public String logout() {
        return "로그아웃 되었!";
    }

    /* 메인페이지로 이동 */
    @GetMapping("/home")
    public String home() {
        return "여기는 원티드 프리온보딩 백엔드 - 사전과제 [wanted wantU right now]의 메인 페이지!";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

}
