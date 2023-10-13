package wanted.freeonboarding.backend.wwrn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.freeonboarding.backend.wwrn.domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
