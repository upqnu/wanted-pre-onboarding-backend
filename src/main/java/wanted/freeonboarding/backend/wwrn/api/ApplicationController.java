package wanted.freeonboarding.backend.wwrn.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Application;
import wanted.freeonboarding.backend.wwrn.domain.dto.ApplicationDto;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.service.ApplicationService;

import java.net.URI;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-posting/{jobPostingId}/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicantRepository applicantRepository;

    @PostMapping("/create")
    public ResponseEntity<Application> createApplication(
            @RequestBody ApplicationDto applicationDto,
            @PathVariable Long jobPostingId,
            Principal principal
    ) {
        String applicantName = principal.getName();
        Applicant applicant = applicantRepository.findByName(applicantName);

        Application newApplication = applicationService.createApplication(applicationDto, jobPostingId, applicant);

        // 생성된 지원서의 URI를 생성. /applicant/{applicantId}/applications/{applyingId}
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{applyingId}")
                .buildAndExpand(newApplication.getApplicationId())
                .toUri();

        // HTTP 상태 코드 201 (Created)와 생성된 지원의 URI를 응답 헤더에 포함하여 반환
        return ResponseEntity
                .created(location)
                .body(newApplication);
    }

    @PatchMapping("{applicationId}/update")
    public ResponseEntity<Application> editApplication(
            @RequestBody ApplicationDto applicationDto,
            @PathVariable Long applicationId
    ) {
        Application updatedApplication = applicationService.updateApplication(applicationDto, applicationId);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("{applicationId}/delete")
    public ResponseEntity<String> deleteApplication(@PathVariable Long applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.ok("지원서 삭제가 완료되었습니다.");
    }
}
