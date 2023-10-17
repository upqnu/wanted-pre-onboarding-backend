package wanted.freeonboarding.backend.wwrn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Applicant findByName(String name);

    Applicant findByEmail(String userEmail);

    @Query("SELECT a.password FROM Applicant a WHERE a.email = :email")
    String findPasswordByEmail(@Param("email") String email);

//    String findPasswordByEmail(String userEmail);
}
