package wanted.freeonboarding.backend.wwrn.security_config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Company;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.repository.CompanyRepository;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final ApplicantRepository applicantRepository;
    private final CompanyRepository companyRepository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(ApplicantRepository applicantRepository, CompanyRepository companyRepository/*, PasswordEncoder passwordEncoder*/) {
        this.applicantRepository = applicantRepository;
        this.companyRepository = companyRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        String role = determineUserRoleByUsername(userEmail);
        String password = getEncodedPasswordForUser(userEmail);

        if (role == null) {
            throw new UsernameNotFoundException("해당 이메일을 사용하는 회사 또는 지원자를 찾을 수 없습니다 : " + userEmail);
        }

        if (password == null) {
            throw new BadCredentialsException("사용자 비밀번호를 찾을 수 없습니다: " + userEmail);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(userEmail)
                .password(password)
                .roles(role)
                .build();
    }

    private String determineUserRoleByUsername(String userEmail) {
        Company company = companyRepository.findByEmail(userEmail);
        log.info(String.valueOf(company + " is a company."));
        Applicant applicant = applicantRepository.findByEmail(userEmail);
        log.info(String.valueOf(applicant + " is an applicant."));

        if (company != null) {
            log.info("User {} is a company.", userEmail);
            return "COMPANY";
        }
        else if (applicant != null) {
            log.info("User {} is an applicant.", userEmail);
            return "APPLICANT";
        }
        else {
            log.info("User {} does not exist.", userEmail);
            return null;
        }
    }

    private String getEncodedPasswordForUser(String userEmail) {
        String companyPassword = companyRepository.findPasswordByEmail(userEmail);
        log.info(String.valueOf(companyPassword + " is company's password."));
        String applicantPassword = applicantRepository.findPasswordByEmail(userEmail);
        log.info(String.valueOf(applicantPassword + " is applicant's password."));

        if (companyPassword != null) {
            log.info("Password {} found for user {} in the company database.", companyPassword, userEmail);
//            return passwordEncoder.encode(companyPassword);
            return encodePassword(companyPassword);
        }
        else if (applicantPassword != null) {
            log.info("Password {} found for user {} in the applicant database.", applicantPassword, userEmail);
//            return passwordEncoder.encode(applicantPassword);
            return encodePassword(applicantPassword);
        }
        else {
            log.info("Password not found for user {}.", userEmail);
            return null;
        }
    }

    // 비밀번호를 인코딩하기 위한 메서드
    private String encodePassword(String password) {
        // 여기에서 passwordEncoder를 사용하여 비밀번호를 인코딩합니다.
        // 예를 들어, BCryptPasswordEncoder를 사용할 수 있습니다.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
