package wanted.freeonboarding.backend.wwrn.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wanted.freeonboarding.backend.wwrn.domain.Application;
import wanted.freeonboarding.backend.wwrn.domain.Company;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;
import wanted.freeonboarding.backend.wwrn.domain.dto.CompanyDto;
import wanted.freeonboarding.backend.wwrn.service.CompanyService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/company", produces = "application/json; charset=utf8")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/join")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto companyDto) {
        Company newCompany = companyService.createCompany(companyDto);

        // 가입이 종료된 후 리다이렉트할 URI 생성
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/home")
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .body(newCompany);
    }

//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @PatchMapping("/{companyId}/update")
    public ResponseEntity<Company> editCompany(@RequestBody CompanyDto companyDto, @PathVariable Long companyId) {
        Company updatedCompany = companyService.editCompany(companyDto, companyId);
        return ResponseEntity.ok(updatedCompany);
    }

//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @DeleteMapping("/{companyId}/delete")
    public ResponseEntity<String> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok("회사 탈퇴가 완료되었습니다.");
    }

//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable Long companyId) {
        Company company = companyService.getCompanyByCompanyId(companyId);
        return ResponseEntity.ok(company);
    }

//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @GetMapping("/{companyId}/job-posting")
    public ResponseEntity<List<JobPosting>> getAllCompanyJobPostings(@PathVariable Long companyId) {
        List<JobPosting> jobPostingList = companyService.getAllCompanyJobPosting(companyId);
        return ResponseEntity.ok(jobPostingList);
    }

//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @GetMapping("/{companyId}/job-posting/{jobPostingId}")
    public ResponseEntity<JobPosting> getSingleCompanyJobPosting(@PathVariable Long companyId, @PathVariable Long jobPostingId) {
        JobPosting jobPosting = companyService.getSingleCompanyJobPosting(companyId, jobPostingId);
        return ResponseEntity.ok(jobPosting);
    }

//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @GetMapping("/{companyId}/job-posting/{jobPostingId}/applications")
    public ResponseEntity<List<Application>> getAllApplicationsPerJobPosting(@PathVariable Long jobPostingId, @PathVariable Long companyId) {
        List<Application> allApplicationsPerJobPosting = companyService.getAllApplicationsPerJobPosting(jobPostingId, companyId);
        return ResponseEntity.ok(allApplicationsPerJobPosting);
    }

//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @GetMapping("/{companyId}/job-posting/{jobPostingId}/applications/{applicationId}")
    public ResponseEntity<Application> getSingleApplicationPerJobPosting(
            @PathVariable Long jobPostingId, @PathVariable Long companyId, @PathVariable Long applicationId) {
        Application singleApplicationPerJobPosting = companyService.getSingleApplicationPerJobPosting(jobPostingId, companyId, applicationId);
        return ResponseEntity.ok(singleApplicationPerJobPosting);
    }
}
