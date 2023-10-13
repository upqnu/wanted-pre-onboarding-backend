package wanted.freeonboarding.backend.wwrn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Applicant findByName(String name);
}
