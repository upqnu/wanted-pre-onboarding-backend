package wanted.freeonboarding.backend.wwrn.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.freeonboarding.backend.wwrn.domain.Company;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;
import wanted.freeonboarding.backend.wwrn.domain.dto.JobPostingCreateDto;
import wanted.freeonboarding.backend.wwrn.domain.dto.JobPostingPatchDto;
import wanted.freeonboarding.backend.wwrn.repository.CompanyRepository;
import wanted.freeonboarding.backend.wwrn.repository.JobPostingRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public JobPosting createJobPosting(JobPostingCreateDto jobPostingCreateDto) {

//        Company postingCreatingCompany = companyRepository.findById(jobPostingCreateDto.getCompanyId())
//                .orElseThrow(() -> new EntityNotFoundException(" [ " + jobPostingCreateDto.getCompanyId() + "번 회사를 찾을 수 없습니다."));

        JobPosting createdJobPosting = JobPosting.builder()
                .postingTitle(jobPostingCreateDto.getPostingTitle())
                .vacantPosition(jobPostingCreateDto.getVacantPosition())
                .jobDescription(jobPostingCreateDto.getJobDescription())
                .language(jobPostingCreateDto.getLanguage())
                .compensation(jobPostingCreateDto.getCompensation())
                .workingNation(jobPostingCreateDto.getWorkingNation())
                .workingCity(jobPostingCreateDto.getWorkingCity())
//                .company(postingCreatingCompany)
                .build();

        createdJobPosting = jobPostingRepository.save(createdJobPosting);

        return createdJobPosting;
    }

    @Transactional
    public JobPosting editJobPosting(JobPostingPatchDto jobPostingPatchDto, Long jobPostingId) {

        JobPosting updatedJobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

//        if (!updatedJobPosting.getCompany().getCompanyId().equals(jobPostingPatchDto.getCompanyId()))
//            throw new EntityNotFoundException(" [ " + jobPostingId + "번 채용공고를 수정할 권한이 없습니다.");

        updatedJobPosting = JobPosting.builder()
                .postingTitle(jobPostingPatchDto.getPostingTitle())
                .vacantPosition(jobPostingPatchDto.getVacantPosition())
                .jobDescription(jobPostingPatchDto.getJobDescription())
                .language(jobPostingPatchDto.getLanguage())
                .compensation(jobPostingPatchDto.getCompensation())
                .workingNation(jobPostingPatchDto.getWorkingNation())
                .workingCity(jobPostingPatchDto.getWorkingCity())
//                .company(updatedJobPosting.getCompany())
                .build();

        updatedJobPosting = jobPostingRepository.save(updatedJobPosting);

        return updatedJobPosting;
    }

    @Transactional
    public void deleteJobPostingById(Long jobPostingId) {

        JobPosting deletedJobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (!deletedJobPosting.getCompany().getCompanyId().equals(currentUserId)) {
//                    jobPostingRepository.deleteById(jobPostingId);
//                }
//            }
//        } else {
//            throw new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 삭제할 권한이 없습니다. ]");
//        }

        jobPostingRepository.deleteById(jobPostingId);
    }

    @Transactional
    public List<JobPosting> getAllJobListing() {
        List<JobPosting> allJobPostings = jobPostingRepository.findAll();
        return allJobPostings;
    }

    /* 채용공고 제목 내에서 검색어로 조회 (대소문자 구분 없이 검색 가능) */
    @Transactional
    public Page<JobPosting> jobPostingByPostingTitle(String searchKeyword, Pageable pageable) {
        return jobPostingRepository.findByPostingTitleContainingIgnoreCase(searchKeyword, pageable);
    }

    /* 채용 포지션 내에서 검색어로 조회 (대소문자 구분 없이 검색 가능) */
    @Transactional
    public Page<JobPosting> jobPostingByVacantPosition(String searchKeyword, Pageable pageable) {
        return jobPostingRepository.findByVacantPositionContainingIgnoreCase(searchKeyword, pageable);
    }

    /* 직무소개 내용 내에서 검색어로 조회 (대소문자 구분 없이 검색 가능) */
    @Transactional
    public Page<JobPosting> jobPostingByJobDescription(String searchKeyword, Pageable pageable) {
        return jobPostingRepository.findByJobDescriptionContainingIgnoreCase(searchKeyword, pageable);
    }

    @Transactional
    public List<JobPosting> getJobPostingByPostingId(Long jobPostingId) {

        List<JobPosting> singleJobPostingAndOthers = new ArrayList<>();

        JobPosting theVeryJobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

        singleJobPostingAndOthers.add(theVeryJobPosting);

        Company postingCompany = theVeryJobPosting.getCompany();
        for (JobPosting posting : postingCompany.getMyJobPosting()) {
            if (posting.getJobPostingId() != jobPostingId) singleJobPostingAndOthers.add(posting);
        }

        return singleJobPostingAndOthers;
    }

}
