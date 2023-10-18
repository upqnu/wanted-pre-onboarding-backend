package wanted.freeonboarding.backend.wwrn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.freeonboarding.backend.wwrn.domain.Application;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<List<Application>> findByJobPosting_JobPostingId(Long jobPostingId);

    Optional<List<Application>> findByApplicant_ApplicantId(Long applicantId);
}
