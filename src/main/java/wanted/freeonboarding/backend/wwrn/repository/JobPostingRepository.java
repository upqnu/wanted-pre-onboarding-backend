package wanted.freeonboarding.backend.wwrn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.freeonboarding.backend.wwrn.domain.JobPosting;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    Optional<List<JobPosting>> findByCompany_CompanyId(Long companyId);
}
