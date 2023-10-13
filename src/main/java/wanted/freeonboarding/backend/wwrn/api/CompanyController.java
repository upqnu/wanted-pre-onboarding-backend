package wanted.freeonboarding.backend.wwrn.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/company")
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

    @PatchMapping("/{companyId}/update")
    public ResponseEntity<Company> editCompany(@RequestBody CompanyDto companyDto, @PathVariable Long companyId) {
        Company updatedCompany = companyService.editCompany(companyDto, companyId);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{companyId}/delete")
    public ResponseEntity<String> deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok("회사 탈퇴가 완료되었습니다.");
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable Long companyId) {
        Company company = companyService.getCompanyByCompanyId(companyId);
        return ResponseEntity.ok(company);
    }

    @GetMapping("/{companyId}/job-posting")
    public ResponseEntity<List<JobPosting>> getCompanyJobPostings(@PathVariable Long companyId) {
        List<JobPosting> jobPostingList = companyService.getAllCompanyJobPosting(companyId);
        return ResponseEntity.ok(jobPostingList);
    }

    @GetMapping("/{companyId}/job-posting/{jobPostingId}/applications")
    public ResponseEntity<List<Application>> getAllApplicationsPerJobPosting(@PathVariable Long jobPostingId) {
        List<Application> allApplicationsPerJobPosting = companyService.getAllApplicationsPerJobPosting(jobPostingId);
        return ResponseEntity.ok(allApplicationsPerJobPosting);
    }
}
