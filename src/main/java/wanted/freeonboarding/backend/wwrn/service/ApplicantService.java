package wanted.freeonboarding.backend.wwrn.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Application;
import wanted.freeonboarding.backend.wwrn.domain.MemberStatus;
import wanted.freeonboarding.backend.wwrn.domain.dto.ApplicantDto;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.repository.ApplicationRepository;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Applicant createApplicant(ApplicantDto applicantDto) {
        Applicant createdApplicant = Applicant.builder()
                .name(applicantDto.getName())
                .email(applicantDto.getEmail())
                .password(passwordEncoder.encode(applicantDto.getPassword()))
                .nationality(applicantDto.getNationality())
                .careers(applicantDto.getCareers())
                .awardsAndOthers(applicantDto.getAwardsAndOthers())
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        createdApplicant = applicantRepository.save(createdApplicant);

        return createdApplicant;
    }

    @Transactional
    public Applicant editUser(ApplicantDto applicantDto, Long applicantId) {
        Applicant updatedApplicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicantId + "번 지원자를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (applicantId.equals(currentUserId)) {
//                    updatedApplicant = Applicant.builder()
//                                .name(updatedApplicant.getName())
//                                .email(updatedApplicant.getEmail())
//                                .password(passwordEncoder.encode(applicantDto.getPassword()))
//                                .nationality(applicantDto.getNationality())
//                                .careers(applicantDto.getCareers())
//                                .awardsAndOthers(applicantDto.getAwardsAndOthers())
//                                .status(MemberStatus.ROLE_APPLICANT)
//                                .build();
//
//                    updatedApplicant = applicantRepository.save(updatedApplicant);
//
//                    return updatedApplicant;
//                }
//            }
//        } else {
//            throw new EntityNotFoundException("[ " + applicantId + "번 지원자 정보를 수정할 권한이 없습니다. ]");
//        }

        updatedApplicant = Applicant.builder()
                .name(updatedApplicant.getName())
                .email(updatedApplicant.getEmail())
                .password(passwordEncoder.encode(applicantDto.getPassword()))
                .nationality(applicantDto.getNationality())
                .careers(applicantDto.getCareers())
                .awardsAndOthers(applicantDto.getAwardsAndOthers())
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        updatedApplicant = applicantRepository.save(updatedApplicant);

        return updatedApplicant;
    }

    @Transactional
    public void deleteApplicant(Long applicantId) {
        Applicant deletedApplicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicantId + "번 지원자를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (applicantId.equals(currentUserId)) {
//                    companyRepository.deleteById(companyId);
//                }
//            }
//        } else {
//            throw new EntityNotFoundException("[ " + applicantId + "번 지원자를 탈퇴시킬 권한이 없습니다. ]");
//        }

        applicantRepository.deleteById(applicantId);
    }

    @Transactional
    public Applicant getApplicantByApplicantId(Long applicantId) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicantId + "번 지원자를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String applicantId = (String) principal;
//
//                if (applicantId.equals(currentUserId)) {
//                    return applicant;
//                }
//            }
//        } else {
//            throw new EntityNotFoundException("[ " + applicantId + "번 지원자 정보를 조회할 권한이 없습니다. ]");
//        }

        return applicant;
    }

    @Transactional
    public List<Application> getAllApplications(Long applicantId) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicantId + "번 지원자를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (!applicantId.equals(currentUserId)) {
//                    throw new EntityNotFoundException("[ " + applicantId + "번 지원자의 지원서를 조회할 권한이 없습니다. ]");
//                }
//            }
//        }

        Optional<List<Application>> allApplicationsOptional = applicationRepository.findByApplicant_ApplicantId(applicantId);
        if (allApplicationsOptional.isPresent()) {
            List<Application> allApplications = allApplicationsOptional.get();
            return allApplications;
        } else {
            throw new EntityNotFoundException("[ 지원내역을 찾을 수 없습니다. ]");
        }
    }

    @Transactional
    public Application getApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicationId + "번 지원서를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (!applicationId.equals(currentUserId)) {
//                    throw new EntityNotFoundException("[ " + applicationId + "번 지원서를 조회할 권한이 없습니다. ]");
//                }
//            }
//        }

        return application;
    }
}
