package wanted.freeonboarding.backend.wwrn.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;
import wanted.freeonboarding.backend.wwrn.domain.dto.JobPostingDto;
import wanted.freeonboarding.backend.wwrn.service.JobPostingService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-posting")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping("/create")
    public ResponseEntity<JobPosting> createJobPosting(@RequestBody JobPostingDto jobPostingDto) {
        JobPosting newJobPosting = jobPostingService.createJobPosting(jobPostingDto);

        // 생성된 채용공고의 URI를 생성. 예: /job-posting/{jobPostingId}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{jobPostingId}")
                .buildAndExpand(newJobPosting.getJobPostingId())
                .toUri();

        // HTTP 상태 코드 201 (Created)와 생성된 채용공고의 URI를 응답 헤더에 포함하여 반환
        return ResponseEntity
                .created(location)
                .body(newJobPosting);
    }

    @PatchMapping("/{jobPostingId}/update")
    public ResponseEntity<JobPosting> editJobPosting(@RequestBody JobPostingDto jobPostingDto, @PathVariable Long jobPostingId) {
        JobPosting updatedJobPosting = jobPostingService.editJobPosting(jobPostingDto, jobPostingId);
        return ResponseEntity.ok(updatedJobPosting);
    }

    @DeleteMapping("/{jobPostingId}/delete")
    public ResponseEntity<String> deleteJobPosting(@PathVariable Long jobPostingId) {
        jobPostingService.deleteJobPostingById(jobPostingId);
        return ResponseEntity.ok("채용공고 삭제가 완료되었습니다.");
    }

    @GetMapping("")
    public ResponseEntity<List<JobPosting>> getJobPostingList() {
        List<JobPosting> allJobPostings = jobPostingService.getAllJobListing();
        return ResponseEntity.ok(allJobPostings);
    }

    @GetMapping("/{jobPostingId}")
    public ResponseEntity<JobPosting> getJobPosting(@PathVariable Long jobPostingId) {
        JobPosting jobPosting = jobPostingService.getJobPostingByPostingId(jobPostingId);
        return ResponseEntity.ok(jobPosting);
    }
}