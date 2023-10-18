## wanted-pre-onboarding-backend

### 1. 아이템 선정 ; 원티드 x 주니어 채용을 목적으로 하는 기업들이 직접 참여하는 [원티드 백엔드 프리온보딩 인턴십] 지원의 사전 과제로 “기업의 채용을 위한 웹 서비스(회사는 채용공고를 생성하고, 이에 사용자는 지원)”가 선정
- 언어는 Java & Spring으로 선택

### 2. 개요

- 프로젝트 명칭 ; wanted wantU right now
- 개발 인원 : 1명
- 개발 기간 : 2023.10.11 ~ 2023.10.18
- 주요 기능 :
    - 서비스에 등록한 회사(Company)들이 채용공고(JobPosting) 생성
    - 서비스를 이용하는 사용자(User)들이 채용공고에 지원
- 개발 언어 : Java 17
- 개발 환경 : SpringBoot 3.1.4, gradle 7.2, Spring Data JPA 3.1.4, Spring Security 6.1.4
- 데이터베이스 : H2 database 2.1.214
- 형상관리 툴 : GitHub

### 3. 요구 사항 분석

- 채용공고 생성 (필수 구현)
    - 로그인한 사용자가 ROLE_COMPANY 권한이 있는 경우에만 생성이 가능하다. 로그인하지 않았거나 ROLE_APPLICANT 권한으로 생성을 시도할 경우 “채용공고를 생성할 권한이 없습니다.” 메시지를 보여준다.
    - 채용공고 제목 (postingTitle), 채용 포지션 (vacantPosition), 직무 내용 (jobDescription), 사용 기술 (language), 채용 시 보상 (compensation), 근무 국가 (workingNation), 근무 도시 (workingCity) 중 하나라도 blank라면 채용공고가 생성되지 않는다. "OOO는 필수 입력 값입니다." 메시지를 보여준다.
- 채용공고 수정 (필수 구현)
    - 해당 채용공고를 생성한 COMPANY만 수정이 가능하다. 로그인하지 않았거나 ROLE_APPLICANT 권한이거나 COMPANY이지만 해당 채용공고를 생성한 회사가 아닌 경우에는 “채용공고를 수정할 권한이 없습니다.”라는 메시지를 보여준다.
    - 채용공고 제목 (postingTitle), 채용 포지션 (vacantPosition), 직무 내용 (jobDescription), 사용 기술 (language), 채용 시 보상 (compensation), 근무 국가 (workingNation), 근무 도시 (workingCity) 중 하나라도 blank라면 채용공고가 생성되지 않는다. "OOO는 필수 입력 값입니다." 메시지를 보여준다.
- 채용공고 삭제 (필수 구현)
    - 해당 채용공고를 생성한 COMPANY만 삭제가 가능하다. 로그인하지 않았거나 ROLE_APPLICANT 권한이거나 COMPANY이지만 해당 채용공고를 생성한 회사가 아닌 경우에는 “채용공고를 삭제할 권한이 없습니다.”라는 메시지를 보여준다.
- 채용공고 리스트 조회 (필수 구현) + 검색 (선택 구현 - 가산점 부가)
    - 모든 채용공고의 리스트 - 각 채용공고별로 모든 필드가 노출된다.
- 개별 채용공고 조회 (필수 구현) + 채용공고 생성한 회사의 다른 채용공고 노출 (선택 구현 - 가산점 부가)
    - 채용공고 리스트에서 개별 채용공고를 클릭하거나 + 채용공고 리스트에서의 검색 결과를 클릭했을 때, 개별 채용공고 조회 페이지로 이동한다.
    - 개별 채용공고 조회 시, 해당 채용공고를 작성한 회사의 다른 모든 채용공고도 같이 노출된다. (조회한 채용공고의 내용이 가장 먼저 노출되고, 나머지 채용공고들은 jobPostingId순으로 노출된다.)
- 채용공고에 유저가 지원 - 지원서 작성 (선택 구현 - 가산점 부가)
    - 유저가 로그인 후 개별 채용공고 조회페이지에서 채용공고에 지원하면 된다.
    - 사용자가 작성해야 할 항목
        - 지원 페이지에서는 지원자의 이름, 이메일, 국적을 자동으로 불러와야 한다. 지원 페이지에서 이 항목들은 수정할 수 없다.
        - 지원 페이지에는 지원서 제목(applyingTitle)을 반드시 작성해야 한다. 글자수는 30자 이내, 미작성 시 "지원서 제목은 필수 입력 값입니다." 메시지를 보여준다.
- 그 외 구현사항
    - username, password 입력을 통한 간단한 로그인 구현 (formLogin)
    - Application run과 동시에 ApplicationRunner인터페이스를 구현한 DummyDataLoader 클래스를 통해 회사, 지원자, 채용공고, 지원서의 dummy data를 각각 5,5,5,10개씩 생성
        - 생성 순서 : 회사, 지원자를 각각 5개씩 생성 → 각각의 회사가 1개씩 채용공고(총 5개)를 생성 → 생성된 총 5개의 채용공고에 대해 지원자가 총 10개의 지원서를 작성 (채용공고별로 작성된 지원서 갯수는 모두 다르고, 지원을 받지 못한 채용공고도 존재하도록 설정)
    - 회사가 자신이 올린 모든 채용공고를 조회 + 개별 채용공고를 조회
    - 회사가 자신이 올린 채용공고별 모든 지원서 리스트를 조회 + 개별 지원서를 조회
    - 사용자의 모든 지원서 리스트를 조회 + 개별 지원서를 조회

### 4. DB 설계

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/783a4501-2457-4a1c-9745-8e0823e83750)

### 5. API 설계

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/3a582bdf-eaf6-4a57-90c2-04ad72934147)

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/86885231-32db-428b-abbf-3587915f1249)

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/fa594ccc-f826-4d24-97d7-0c07affeb644)

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/86d25fc0-1b85-42fe-8ffe-656313cfdd84)

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/209899ce-4303-4867-8489-d109de30079e)
