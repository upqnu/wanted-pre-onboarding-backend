package wanted.freeonboarding.backend.wwrn.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.freeonboarding.backend.wwrn.domain.Application;
import wanted.freeonboarding.backend.wwrn.domain.Company;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;
import wanted.freeonboarding.backend.wwrn.domain.MemberStatus;
import wanted.freeonboarding.backend.wwrn.domain.dto.CompanyDto;
import wanted.freeonboarding.backend.wwrn.repository.ApplicationRepository;
import wanted.freeonboarding.backend.wwrn.repository.CompanyRepository;
import wanted.freeonboarding.backend.wwrn.repository.JobPostingRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public Company createCompany(CompanyDto companyDto) {
        Company createdCompany = Company.builder()
                .email(companyDto.getEmail())
                .password(companyDto.getPassword())
                .name(companyDto.getName())
                .headOfficeNation(companyDto.getHeadOfficeNation())
                .headOfficeCity(companyDto.getHeadOfficeCity())
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        createdCompany = companyRepository.save(createdCompany);

        return createdCompany;
    }

    @Transactional
    public Company editCompany(CompanyDto companyDto, Long companyId) {
        Company updatedCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + companyId + "번 회사를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (companyId.equals(currentUserId)) {
//                    updatedCompany = Company.builder()
//                            .email(updatedCompany.getEmail())
//                            .password(companyDto.getPassword())
//                            .name(updatedCompany.getName())
//                            .headOfficeNation(companyDto.getHeadOfficeNation())
//                            .headOfficeCity(companyDto.getHeadOfficeCity())
//                            .status(updatedCompany.getStatus())
//                            .build();
//
//                    updatedCompany = companyRepository.save(updatedCompany);
//
//                    return updatedCompany;
//                }
//            }
//        } else {
//            throw new EntityNotFoundException("[ " + companyId + "번 회사 정보를 수정할 권한이 없습니다. ]");
//        }

        updatedCompany = Company.builder()
                .email(updatedCompany.getEmail())
                .password(companyDto.getPassword())
                .name(updatedCompany.getName())
                .headOfficeNation(companyDto.getHeadOfficeNation())
                .headOfficeCity(companyDto.getHeadOfficeCity())
                .status(updatedCompany.getStatus())
                .build();

        updatedCompany = companyRepository.save(updatedCompany);

        return updatedCompany;
    }

    @Transactional
    public void deleteCompany(Long companyId) {
        Company deletedCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + companyId + "번 회사를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (companyId.equals(currentUserId)) {
//                    companyRepository.deleteById(companyId);
//                }
//            }
//        } else {
//            throw new EntityNotFoundException("[ " + companyId + "번 회사를 탈퇴시킬 권한이 없습니다. ]");
//        }

        companyRepository.deleteById(companyId);
    }

    @Transactional
    public Company getCompanyByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + companyId + "번 회사를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (companyId.equals(currentUserId)) {
//                    return company;
//                }
//            }
//        } else {
//            throw new EntityNotFoundException("[ " + companyId + "번 회사 정보를 조회할 권한이 없습니다. ]");
//        }

        return company;
    }

    @Transactional
    public List<JobPosting> getAllCompanyJobPosting(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + companyId + "번 회사를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (!companyId.equals(currentUserId)) {
//                    throw new EntityNotFoundException("[ " + companyId + "번 회사의 채용공고를 조회할 권한이 없습니다. ]");
//                }
//            }
//        }

        Optional<List<JobPosting>> jobPostingListOptional = jobPostingRepository.findByCompany_CompanyId(companyId);
        if (jobPostingListOptional.isPresent()) {
            List<JobPosting> jobPostingList = jobPostingListOptional.get();
            return jobPostingList;
        } else {
            throw new EntityNotFoundException("[ 등록된 채용공고가 없습니다. ]");
        }
    }

    @Transactional
    public JobPosting getSingleCompanyJobPosting(Long companyId, Long jobPostingId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + companyId + "번 회사를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (!companyId.equals(currentUserId)) {
//                    throw new EntityNotFoundException("[ " + companyId + "번 회사의 채용공고를 조회할 권한이 없습니다. ]");
//                }
//            }
//        }

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

        return jobPosting;
    }

    @Transactional
    public List<Application> getAllApplicationsPerJobPosting(Long jobPostingId, Long companyId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (!companyId.equals(currentUserId)) {
//                    throw new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 조회할 권한이 없습니다. ]");
//                }
//            }
//        }

        Optional<List<Application>> allApplicationsPerJobPostingOptional = applicationRepository.findByJobPosting_JobPostingId(jobPostingId);
        if (allApplicationsPerJobPostingOptional.isPresent()) {
            List<Application> allApplicationsPerJobPosting = allApplicationsPerJobPostingOptional.get();
            return allApplicationsPerJobPosting;
        } else {
            throw new EntityNotFoundException("[ 채용공고에 지원자가 아직 없습니다. ]");
        }
    }

    @Transactional
    public Application getSingleApplicationPerJobPosting(Long jobPostingId, Long companyId, Long applicationId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + jobPostingId + "번 채용공고를 찾을 수 없습니다. ]"));

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof String) {
//                // principal이 String인 경우 (대부분의 경우), 이는 사용자의 ID
//                String currentUserId = (String) principal;
//
//                if (!companyId.equals(currentUserId)) {
//                    throw new EntityNotFoundException("[ " + jobPostingId + "번 채용공고에 지원한 지원자를 조회할 권한이 없습니다. ]");
//                }
//            }
//        }

        Application singleApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("[ " + applicationId + "번 지원서를 찾을 수 없습니다. ]"));

        return singleApplication;
    }

}
