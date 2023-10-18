package wanted.freeonboarding.backend.wwrn.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Application;
import wanted.freeonboarding.backend.wwrn.domain.dto.ApplicantDto;
import wanted.freeonboarding.backend.wwrn.service.ApplicantService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/applicant", produces = "application/json; charset=utf8")
public class ApplicantController {

    private final ApplicantService applicantService;

    /* 지원자 회원 가입(생성) */
    @PostMapping("/join")
    public ResponseEntity<Applicant> createUser(@RequestBody ApplicantDto applicantDto) {
        Applicant newApplicant = applicantService.createApplicant(applicantDto);

        // 가입이 종료된 후 리다이렉트할 URI 생성
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/home")
                .build()
                .toUri();

        // HTTP 상태 코드 201 (Created)와 회원가입한 사용자의 URI를 응답 헤더에 포함하여 반환
        return ResponseEntity
                .created(location)
                .body(newApplicant);
    }

    /* 지원자 정보 수정 */
//    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    @PatchMapping("/{applicantId}/update")
    public ResponseEntity<Applicant> editUser(@RequestBody ApplicantDto applicantDto, @PathVariable Long applicantId) {
        Applicant updateApplicant = applicantService.editUser(applicantDto, applicantId);
        return ResponseEntity.ok(updateApplicant);
    }

    /* 지원자 회원 탈퇴(삭제) */
//    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    @DeleteMapping("/{applicantId}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long applicantId) {
        applicantService.deleteApplicant(applicantId);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    /* 지원자가 자신의 정보 조회 */
//    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    @GetMapping("/{applicantId}")
    public ResponseEntity<Applicant> getUser(@PathVariable Long applicantId) {
        Applicant applicant = applicantService.getApplicantByApplicantId(applicantId);
        return ResponseEntity.ok(applicant);
    }

    /* 지원자가 작성한 모든 지원서 조회 */
//    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    @GetMapping("/{applicantId}/applications")
    public ResponseEntity<List<Application>> getAllApplications(@PathVariable Long applicantId) {
        List<Application> allApplications = applicantService.getAllApplications(applicantId);
        return ResponseEntity.ok(allApplications);
    }

    /* 지원자가 작성한 개별 지원서 조회 */
//    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    @GetMapping("/{applicantId}/applications/{applicationId}")
    public ResponseEntity<Application> getApplication(@PathVariable Long applicationId) {
        Application application = applicantService.getApplication(applicationId);
        return ResponseEntity.ok(application);
    }
}
