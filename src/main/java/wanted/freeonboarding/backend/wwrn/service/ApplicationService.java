package wanted.freeonboarding.backend.wwrn.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Application;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;
import wanted.freeonboarding.backend.wwrn.domain.dto.ApplicationDto;
import wanted.freeonboarding.backend.wwrn.repository.ApplicationRepository;
import wanted.freeonboarding.backend.wwrn.repository.JobPostingRepository;

@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPostingRepository jobPostingRepository;

    @Transactional
    public Application createApplication(ApplicationDto applicationDto, Long jobPostingId, Applicant applicant) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

        Application newApplication = Application.builder()
                .jobPosting(jobPosting)
                .applicant(applicant)
                .applyingTitle(applicationDto.getApplyingTitle())
                .applyingIntroduction(applicationDto.getApplyingIntroduction())
                .applyingCareers(applicationDto.getApplyingCareers())
                .applyingAwardsOthers(applicationDto.getApplyingAwardsOthers())
                .build();

        newApplication = applicationRepository.save(newApplication);

        return newApplication;
    }

    @Transactional
    public Application updateApplication(ApplicationDto applicationDto, Long applicationId) {
        Application updatedApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicationId + "번 지원서를 찾을 수 없습니다. ]"));

        updatedApplication = Application.builder()
                .applyingTitle(applicationDto.getApplyingTitle())
                .applyingIntroduction(applicationDto.getApplyingIntroduction())
                .applyingCareers(applicationDto.getApplyingCareers())
                .applyingAwardsOthers(applicationDto.getApplyingAwardsOthers())
                .build();

        updatedApplication = applicationRepository.save(updatedApplication);

        return updatedApplication;
    }

    @Transactional
    public void deleteApplication(Long applicationId) {
        Application deletedApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicationId + "번 지원서 를 찾을 수 없습니다. ]"));

        applicationRepository.deleteById(applicationId);
    }

}
