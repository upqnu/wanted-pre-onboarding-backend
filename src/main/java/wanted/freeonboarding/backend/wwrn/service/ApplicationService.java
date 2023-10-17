package wanted.freeonboarding.backend.wwrn.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Application;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;
import wanted.freeonboarding.backend.wwrn.domain.dto.ApplicationCreateDto;
import wanted.freeonboarding.backend.wwrn.domain.dto.ApplicationPatchDto;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.repository.ApplicationRepository;
import wanted.freeonboarding.backend.wwrn.repository.JobPostingRepository;

import java.security.Principal;

@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ApplicantRepository applicantRepository;

    @Transactional
    public Application createApplication(ApplicationCreateDto applicationCreateDto, Long jobPostingId) {

//        Applicant applyingApplicant = applicantRepository.findById(applicationCreateDto.getApplicantId())
//                .orElseThrow(() -> new EntityNotFoundException(" [ " + applicationCreateDto.getApplicantId() + "번 지원자를 찾을 수 없습니다."));

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

        Application newApplication = Application.builder()
                .jobPosting(jobPosting)
//                .applicant(applyingApplicant)
                .applyingTitle(applicationCreateDto.getApplyingTitle())
                .applyingIntroduction(applicationCreateDto.getApplyingIntroduction())
                .applyingCareers(applicationCreateDto.getApplyingCareers())
                .applyingAwardsOthers(applicationCreateDto.getApplyingAwardsOthers())
                .build();

        newApplication = applicationRepository.save(newApplication);

        return newApplication;
    }

    @Transactional
    public Application updateApplication(ApplicationPatchDto applicationPatchDto, Long applicationId, Long jobPostingId) {
        Application updatedApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicationId + "번 지원서를 찾을 수 없습니다. ]"));

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

//        if (!updatedApplication.getApplicant().getApplicantId().equals(applicationPatchDto.getApplicantId()))
//            throw new EntityNotFoundException(" [ " + applicationId + "번 채용공고를 수정할 권한이 없습니다.");

        updatedApplication = Application.builder()
                .jobPosting(jobPosting)
//                .applicant(updatedApplication.getApplicant())
                .applyingTitle(applicationPatchDto.getApplyingTitle())
                .applyingIntroduction(applicationPatchDto.getApplyingIntroduction())
                .applyingCareers(applicationPatchDto.getApplyingCareers())
                .applyingAwardsOthers(applicationPatchDto.getApplyingAwardsOthers())
                .build();

        updatedApplication = applicationRepository.save(updatedApplication);

        return updatedApplication;
    }

    @Transactional
    public void deleteApplication(Long applicationId, Long jobPostingId) {
        Application deletedApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicationId + "번 지원서 를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (!deletedApplication.getApplicant().getApplicantId().equals(currentUserId)) {
//                    applicationRepository.deleteById(applicationId);
//                }
//            }
//        } else {
//            throw new EntityNotFoundException("[ " + applicationId + "번 지원서를 삭제할 권한이 없습니다. ]");
//        }

        applicationRepository.deleteById(applicationId);
    }

}
