package wanted.freeonboarding.backend.wwrn.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;
import wanted.freeonboarding.backend.wwrn.domain.dto.JobPostingDto;
import wanted.freeonboarding.backend.wwrn.repository.JobPostingRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    @Transactional
    public JobPosting createJobPosting(JobPostingDto jobPostingDto) {

        JobPosting createdJobPosting = JobPosting.builder()
                .postingTitle(jobPostingDto.getPostingTitle())
                .vacantPosition(jobPostingDto.getVacantPosition())
                .jobDescription(jobPostingDto.getJobDescription())
                .language(jobPostingDto.getLanguage())
                .compensation(jobPostingDto.getCompensation())
                .workingNation(jobPostingDto.getWorkingNation())
                .workingCity(jobPostingDto.getWorkingCity())
                .build();

        createdJobPosting = jobPostingRepository.save(createdJobPosting);

        return createdJobPosting;
    }

    @Transactional
    public JobPosting editJobPosting(JobPostingDto jobPostingDto, Long jobPostingId) {
        JobPosting updatedJobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

        updatedJobPosting = JobPosting.builder()
                .postingTitle(jobPostingDto.getPostingTitle())
                .vacantPosition(jobPostingDto.getVacantPosition())
                .jobDescription(jobPostingDto.getJobDescription())
                .language(jobPostingDto.getLanguage())
                .compensation(jobPostingDto.getCompensation())
                .workingNation(jobPostingDto.getWorkingNation())
                .workingCity(jobPostingDto.getWorkingCity())
                .build();

        updatedJobPosting = jobPostingRepository.save(updatedJobPosting);

        return updatedJobPosting;
    }

    @Transactional
    public void deleteJobPostingById(Long jobPostingId) {
        JobPosting deletedJobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

        jobPostingRepository.deleteById(jobPostingId);
    }

    @Transactional
    public List<JobPosting> getAllJobListing() {
        List<JobPosting> allJobPostings = jobPostingRepository.findAll();
        return allJobPostings;
    }

    @Transactional
    public JobPosting getJobPostingByPostingId(Long jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

        return jobPosting;
    }
}
