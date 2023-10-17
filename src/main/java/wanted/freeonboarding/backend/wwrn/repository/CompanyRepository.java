package wanted.freeonboarding.backend.wwrn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wanted.freeonboarding.backend.wwrn.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByEmail(String userEmail);

    @Query("SELECT c.password FROM Company c WHERE c.email = :email")
    String findPasswordByEmail(@Param("email") String email);

//    @Query("SELECT c.companyId FROM Company c WHERE c.companyId = :companyId")
//    Long findCompany_ByCompanyId(@Param("companyId") Long jobPosting);
}
