package wanted.freeonboarding.backend.wwrn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wanted.freeonboarding.backend.wwrn.domain.*;
import wanted.freeonboarding.backend.wwrn.repository.ApplicantRepository;
import wanted.freeonboarding.backend.wwrn.repository.ApplicationRepository;
import wanted.freeonboarding.backend.wwrn.repository.CompanyRepository;
import wanted.freeonboarding.backend.wwrn.repository.JobPostingRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HomeService {

    private final CompanyRepository companyRepository;
    private final ApplicantRepository applicantRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;

    public void createDummyData() {
//        createDummyCompanies();
//        createDummyApplicants();
//        createDummyJobPostings();
        createDummyApplications();
    }

    private List<Company> createDummyCompanies() {
        List<Company> companies = new ArrayList<>();

        Company company1 = Company.builder()
                .name("company1")
                .email("company1@email.com")
                .password(passwordEncoder.encode("1234"))
                .headOfficeNation("한국")
                .headOfficeCity("서울")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        Company company2 = Company.builder()
                .name("company2")
                .email("company2@email.com")
                .password(passwordEncoder.encode("1234"))
                .headOfficeNation("한국")
                .headOfficeCity("판교")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        Company company3 = Company.builder()
                .name("company3")
                .email("company3@email.com")
                .password(passwordEncoder.encode("1234"))
                .headOfficeNation("라트비아")
                .headOfficeCity("리가")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        Company company4 = Company.builder()
                .name("company4")
                .email("company4@email.com")
                .password(passwordEncoder.encode("1234"))
                .headOfficeNation("가나")
                .headOfficeCity("아크라")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        Company company5 = Company.builder()
                .name("company5")
                .email("company5@email.com")
                .password(passwordEncoder.encode("1234"))
                .headOfficeNation("아르헨티나")
                .headOfficeCity("부에노스아이레스")
                .status(MemberStatus.ROLE_COMPANY)
                .build();

        companies.addAll(companyRepository.saveAll(List.of(company1, company2, company3, company4, company5)));

        return companies;
    }

    private List<Applicant> createDummyApplicants() {
        List<Applicant> applicants = new ArrayList<>();

        Applicant applicant1 = Applicant.builder()
                .name("applicant1")
                .email("applicant1@email.com")
                .password(passwordEncoder.encode("4321"))
                .nationality("짐바브웨")
                .careers("career1")
                .awardsAndOthers("award1")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        Applicant applicant2 = Applicant.builder()
                .name("applicant2")
                .email("applicant2@email.com")
                .password(passwordEncoder.encode("4321"))
                .nationality("태국")
                .careers("career2")
                .awardsAndOthers("award2")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        Applicant applicant3 = Applicant.builder()
                .name("applicant3")
                .email("applicant3@email.com")
                .password(passwordEncoder.encode("4321"))
                .nationality("캐나다")
                .careers("career3")
                .awardsAndOthers("award3")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        Applicant applicant4 = Applicant.builder()
                .name("applicant4")
                .email("applicant4@email.com")
                .password(passwordEncoder.encode("4321"))
                .nationality("포르투갈")
                .careers("career4")
                .awardsAndOthers("award4")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        Applicant applicant5 = Applicant.builder()
                .name("applicant5")
                .email("applicant5@email.com")
                .password(passwordEncoder.encode("4321"))
                .nationality("몽골")
                .careers("career5")
                .awardsAndOthers("award5")
                .status(MemberStatus.ROLE_APPLICANT)
                .build();

        applicants.addAll(applicantRepository.saveAll(List.of(applicant1, applicant2, applicant3, applicant4, applicant5)));

        return applicants;
    }

    private List<JobPosting> createDummyJobPostings() {
        List<JobPosting> jobPostings = new ArrayList<>();

        List<Company> companies = createDummyCompanies();

        JobPosting jobPosting1 = JobPosting.builder()
                .postingTitle("Software Engineer")
                .company(companies.get(0))
                .vacantPosition("Software Developer")
                .jobDescription("We are looking for a software engineer to join our team.")
                .language("Java")
                .compensation(8000)
                .workingNation("United States")
                .workingCity("San Francisco")
                .build();

        JobPosting jobPosting2 = JobPosting.builder()
                .postingTitle("Data Analyst")
                .company(companies.get(1))
                .vacantPosition("Data Analyst")
                .jobDescription("Join our data analysis team and make an impact.")
                .language("Python")
                .compensation(7000)
                .workingNation("Canada")
                .workingCity("Toronto")
                .build();

        JobPosting jobPosting3 = JobPosting.builder()
                .postingTitle("Web Developer")
                .company(companies.get(2))
                .vacantPosition("Front-end Developer")
                .jobDescription("Looking for a web developer to create amazing websites.")
                .language("JavaScript")
                .compensation(7500)
                .workingNation("Germany")
                .workingCity("Berlin")
                .build();

        JobPosting jobPosting4 = JobPosting.builder()
                .postingTitle("Marketing Manager")
                .company(companies.get(3))
                .vacantPosition("Marketing Manager")
                .jobDescription("Lead our marketing team to success.")
                .language("Marketing")
                .compensation(9000)
                .workingNation("United Kingdom")
                .workingCity("London")
                .build();

        JobPosting jobPosting5 = JobPosting.builder()
                .postingTitle("Product Designer")
                .company(companies.get(4))
                .vacantPosition("UI/UX Designer")
                .jobDescription("Design user-friendly products for our clients.")
                .language("Design")
                .compensation(8000)
                .workingNation("Spain")
                .workingCity("Barcelona")
                .build();

        JobPosting jobPosting6 = JobPosting.builder()
                .postingTitle("PHP Engineer")
                .company(companies.get(0))
                .vacantPosition("PHP Developer")
                .jobDescription("We are looking for a PHP engineer to join our team.")
                .language("PHP")
                .compensation(80000)
                .workingNation("UAE")
                .workingCity("Dubai")
                .build();

        JobPosting jobPosting7 = JobPosting.builder()
                .postingTitle("ERP Engineer")
                .company(companies.get(0))
                .vacantPosition("ERP Developer")
                .jobDescription("We are looking for a ERP engineer to join our team.")
                .language("ERP")
                .compensation(80000)
                .workingNation("Korea")
                .workingCity("Pangyo")
                .build();

        jobPostings.addAll(jobPostingRepository.saveAll(List.of(
                jobPosting1, jobPosting2, jobPosting3, jobPosting4, jobPosting5, jobPosting6, jobPosting7
        )));

        return jobPostings;
    }

    private void createDummyApplications() {
        List<Applicant> applicants = createDummyApplicants();
        List<JobPosting> jobPostings = createDummyJobPostings();

        Application application1 = Application.builder()
                .jobPosting(jobPostings.get(0))
                .applicant(applicants.get(0))
                .applyingTitle("Pick me! Pick me! Pick me up! 1")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 1")
                .applyingCareers("뽑힐 만한 경력 1")
                .applyingAwardsOthers("받을 만큼 받은 상 1")
                .build();

        Application application2 = Application.builder()
                .jobPosting(jobPostings.get(0))
                .applicant(applicants.get(1))
                .applyingTitle("Pick me! Pick me! Pick me up! 2")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 2")
                .applyingCareers("뽑힐 만한 경력 2")
                .applyingAwardsOthers("받을 만큼 받은 상 2")
                .build();

        Application application3 = Application.builder()
                .jobPosting(jobPostings.get(0))
                .applicant(applicants.get(2))
                .applyingTitle("Pick me! Pick me! Pick me up! 3")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 3")
                .applyingCareers("뽑힐 만한 경력 3")
                .applyingAwardsOthers("받을 만큼 받은 상 3")
                .build();

        Application application4 = Application.builder()
                .jobPosting(jobPostings.get(0))
                .applicant(applicants.get(3))
                .applyingTitle("Pick me! Pick me! Pick me up! 4")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 4")
                .applyingCareers("뽑힐 만한 경력 4")
                .applyingAwardsOthers("받을 만큼 받은 상 4")
                .build();

        Application application5 = Application.builder()
                .jobPosting(jobPostings.get(1))
                .applicant(applicants.get(4))
                .applyingTitle("Pick me! Pick me! Pick me up! 5")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 5")
                .applyingCareers("뽑힐 만한 경력 5")
                .applyingAwardsOthers("받을 만큼 받은 상 5")
                .build();

        Application application6 = Application.builder()
                .jobPosting(jobPostings.get(1))
                .applicant(applicants.get(0))
                .applyingTitle("Pick me! Pick me! Pick me up! 6")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 6")
                .applyingCareers("뽑힐 만한 경력 6")
                .applyingAwardsOthers("받을 만큼 받은 상 6")
                .build();

        Application application7 = Application.builder()
                .jobPosting(jobPostings.get(1))
                .applicant(applicants.get(1))
                .applyingTitle("Pick me! Pick me! Pick me up! 7")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 7")
                .applyingCareers("뽑힐 만한 경력 7")
                .applyingAwardsOthers("받을 만큼 받은 상 7")
                .build();

        Application application8 = Application.builder()
                .jobPosting(jobPostings.get(2))
                .applicant(applicants.get(2))
                .applyingTitle("Pick me! Pick me! Pick me up! 8")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 8")
                .applyingCareers("뽑힐 만한 경력 8")
                .applyingAwardsOthers("받을 만큼 받은 상 8")
                .build();

        Application application9 = Application.builder()
                .jobPosting(jobPostings.get(2))
                .applicant(applicants.get(3))
                .applyingTitle("Pick me! Pick me! Pick me up! 9")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 9")
                .applyingCareers("뽑힐 만한 경력 9")
                .applyingAwardsOthers("받을 만큼 받은 상 9")
                .build();

        Application application10 = Application.builder()
                .jobPosting(jobPostings.get(3))
                .applicant(applicants.get(4))
                .applyingTitle("Pick me! Pick me! Pick me up! 10")
                .applyingIntroduction("날 뽑는 당신의 열정이 곧 당신 회사의 운명을 결정 10")
                .applyingCareers("뽑힐 만한 경력 10")
                .applyingAwardsOthers("받을 만큼 받은 상 10")
                .build();

        applicationRepository.saveAll(List.of(
                application1, application2, application3, application4, application5,
                application6, application7, application8, application9, application10
        ));
    }
}
