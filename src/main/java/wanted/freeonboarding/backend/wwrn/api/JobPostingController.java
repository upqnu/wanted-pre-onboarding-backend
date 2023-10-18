package wanted.freeonboarding.backend.wwrn.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;
import wanted.freeonboarding.backend.wwrn.domain.dto.JobPostingCreateDto;
import wanted.freeonboarding.backend.wwrn.domain.dto.JobPostingPatchDto;
import wanted.freeonboarding.backend.wwrn.service.JobPostingService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/job-posting", produces = "application/json; charset=utf8")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    /* 채용공고 작성 */
//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @PostMapping("/create")
    public ResponseEntity<JobPosting> createJobPosting(@RequestBody JobPostingCreateDto jobPostingCreateDtoDto) {
        JobPosting newJobPosting = jobPostingService.createJobPosting(jobPostingCreateDtoDto);

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

    /* 채용공고 수정 */
//    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @PatchMapping("/{jobPostingId}/update")
    public ResponseEntity<JobPosting> editJobPosting(@RequestBody JobPostingPatchDto jobPostingPatchDto, @PathVariable Long jobPostingId) {
        JobPosting updatedJobPosting = jobPostingService.editJobPosting(jobPostingPatchDto, jobPostingId);
        return ResponseEntity.ok(updatedJobPosting);
    }

    /* 채용공고 삭제 */
    //    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @DeleteMapping("/{jobPostingId}/delete")
    public ResponseEntity<String> deleteJobPosting(@PathVariable Long jobPostingId) {
        jobPostingService.deleteJobPostingById(jobPostingId);
        return ResponseEntity.ok("채용공고 삭제가 완료되었습니다.");
    }

    /* 모든 채용공고 조회 */
    @GetMapping("")
    public ResponseEntity<List<JobPosting>> getJobPostingList() {
        List<JobPosting> allJobPostings = jobPostingService.getAllJobListing();
        return ResponseEntity.ok(allJobPostings);
    }

    /* 채용공고 리스트에서 검색 */
    @GetMapping("/search")
    public ResponseEntity<Page<JobPosting>> searchJobPosting(Pageable pageable, String searchKeyword) {

        List<JobPosting> tmpPostingList = jobPostingService.getAllJobListing();

        // 페이지 번호, 페이지 크기 및 정렬 방법 설정
        int pageNumber = 0; // 원하는 페이지 번호 (0부터 시작)
        int pageSize = 5; // 원하는 페이지 크기
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<JobPosting> searchList = null;

        if (searchKeyword == null) {
            searchList = new PageImpl<>(tmpPostingList, pageRequest, tmpPostingList.size());
        } else {
            Page<JobPosting> tmpSearchList;
            tmpSearchList = jobPostingService.jobPostingByPostingTitle(searchKeyword, pageable);
            if (tmpSearchList.getNumberOfElements() > 0) searchList = tmpSearchList;
            else {
                tmpSearchList = jobPostingService.jobPostingByVacantPosition(searchKeyword, pageable);
                if (tmpSearchList.getNumberOfElements() > 0) searchList = tmpSearchList;
                else searchList = jobPostingService.jobPostingByJobDescription(searchKeyword, pageable);
            }
        }

        return ResponseEntity.ok(searchList);
    }

    /* 채용공고 개별 조회 */
    @GetMapping("/{jobPostingId}")
    public ResponseEntity<List<JobPosting>> getJobPosting(@PathVariable Long jobPostingId) {
        List<JobPosting> singleJobPostingAndOthers = jobPostingService.getJobPostingByPostingId(jobPostingId);
        return ResponseEntity.ok(singleJobPostingAndOthers);
    }
}
