package wanted.freeonboarding.backend.wwrn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.freeonboarding.backend.wwrn.domain.Applicant;
import wanted.freeonboarding.backend.wwrn.domain.Company;
import wanted.freeonboarding.backend.wwrn.domain.MemberStatus;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.repository.CompanyRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HomeService {

    private final CompanyRepository companyRepository;
    private final ApplicantRepository applicantRepository;

    public void createDummyData() {
        createDummyCompanies();
        createDummyApplicants();
    }

    private void createDummyCompanies() {
        Company company1 = Company.builder()
                .name("company1")
                .email("company1@email.com")
                .password("1234")
                .headOfficeNation("한국")
                .headOfficeCity("서울")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        Company company2 = Company.builder()
                .name("company2")
                .email("company2@email.com")
                .password("1234")
                .headOfficeNation("한국")
                .headOfficeCity("판교")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        Company company3 = Company.builder()
                .name("company3")
                .email("company3@email.com")
                .password("1234")
                .headOfficeNation("라트비아")
                .headOfficeCity("리가")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        Company company4 = Company.builder()
                .name("company4")
                .email("company4@email.com")
                .password("1234")
                .headOfficeNation("가나")
                .headOfficeCity("아크라")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        Company company5 = Company.builder()
                .name("company5")
                .email("company5@email.com")
                .password("1234")
                .headOfficeNation("아르헨티나")
                .headOfficeCity("부에노스아이레스")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        companyRepository.saveAll(List.of(company1, company2, company3, company4, company5));
    }

    private void createDummyApplicants() {
        Applicant applicant1 = Applicant.builder()
                .name("applicant1")
                .email("applicant1@email.com")
                .password("4321")
                .nationality("짐바브웨")
                .careers("career1")
                .awardsAndOthers("award1")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        Applicant applicant2 = Applicant.builder()
                .name("applicant2")
                .email("applicant2@email.com")
                .password("4321")
                .nationality("태국")
                .careers("career2")
                .awardsAndOthers("award2")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        Applicant applicant3 = Applicant.builder()
                .name("applicant3")
                .email("applicant3@email.com")
                .password("4321")
                .nationality("캐나다")
                .careers("career3")
                .awardsAndOthers("award3")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        Applicant applicant4 = Applicant.builder()
                .name("applicant4")
                .email("applicant4@email.com")
                .password("4321")
                .nationality("포르투갈")
                .careers("career4")
                .awardsAndOthers("award4")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        Applicant applicant5 = Applicant.builder()
                .name("applicant5")
                .email("applicant5@email.com")
                .password("4321")
                .nationality("몽골")
                .careers("career5")
                .awardsAndOthers("award5")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        applicantRepository.saveAll(List.of(applicant1, applicant2, applicant3, applicant4, applicant5));
    }
}
