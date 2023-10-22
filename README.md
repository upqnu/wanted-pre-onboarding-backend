## wanted-pre-onboarding-backend

#### (이 README는 2023.10.11~18에 진행된 - 브랜치 'dev1'에 구현된 사항에 대한 정리입니다.)

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

### 4. 구현 과정

- 총 4개의 entity : 회사(Company), 지원자(Applicant), 채용공고(JobPosting), 지원서(Application)
    - 아래의 설명에서 ‘사용자’는 회사와 지원자를 모두 지칭함.
- 사용자 이메일 및 패스워드 입력을 통한 로그인 구현 (formLogin)
    - 회사의 로그인, 지원자의 로그인 엔드포인트는 별도로 작성
    - 다만 로그인한 사용자의 권한을 로드해올 수 없어 → 회사id, 지원자id가 필요한 API 중 일부는 현재 기능이 제한적으로 구현되고 있다. (자세한 사항은 아래 토글을 열어 참고 가능)
        - <details>

            (아래 기능 중 ‘에러가 발생’했다고 명시되지 않은 기능은 현재 코드로는 제한적으로 또는 문제 없이 기능이 수행되고 있음)

            - 채용공고 생성 ; jobPostingCreateDto에서 companyId를 가져오지 못해 → 생성된 채용공고에 company 정보를 등록하지 못함
            - 채용공고 수정 ; jobPostingPatchDto에서 companyId를 가져오지 못해 → 채용공고를 수정하려는 회사가 실제 채용공고를 생성된 회사인지 알 수 없음
                - 따라서 이미 생성된 채용공고를 수정한다고 해도 → 새로운 채용공고가 생성된다
            - 채용공고 삭제 ; Authentication을 통해 companyId를 가져오지 못해 → 채용공고를 삭제하려는 회사가 실제 채용공고를 생성된 회사인지 알 수 없음
            - (모든 채용공고 조회는 이상이 없음)
            - (개별 채용공고 조회는 이상이 없음)
            - 지원서 생성 ; applicationCreateDto에서 ApplicantId를 가져오지 못해 → 생성된 지원서에 applicant 정보를 등록하지 못함
                - 시도하면 404 에러 발생 (o.s.web.servlet.PageNotFound : No mapping for POST /job-posting/1/application/create)
            - 지원서 수정 ; applicationCreateDto에서 ApplicantId를 가져오지 못해 → 지원서를 수정하려는 지원자가 실제 지원서를 작성한 회사인지 알 수 없음
                - 시도하면 404 에러 발생 (o.s.web.servlet.PageNotFound : No mapping for PATCH /job-posting/1/application/1/update)
            - 지원서 삭제 ; Authentication을 통해 ApplicantId를 가져오지 못해 → 지원서를 삭제하려는 지원자가 실제 지원서를 작성한 지원자인지 알 수 없음
                - 시도하면 404 에러 발생 (o.s.web.servlet.PageNotFound : No mapping for DELETE /job-posting/1/application/4/delete)
            - (회사 생성은 이상이 없음)
            - 회사 수정 ; Authentication을 통해 companyId를 가져오지 못해 → 회사 정보를 수정하려는 사용자가 회사정보를 실제 생성한 그 회사인지 알 수 없음
                - 따라서 이미 생성된 회사를 수정한다고 해도 → 새로운 회사가 생성된다
            - 회사 삭제 ; Authentication을 통해 companyId를 가져오지 못해 → 회사 정보를 삭제하려는 사용자가 회사정보를 실제 생성한 그 회사인지 알 수 없음
            - 개별 회사의 정보 확인 ; Authentication을 통해 companyId를 가져오지 못해 → 회사 정보를 확인하려는 사용자가 회사정보를 실제 생성한 그 회사인지 알 수 없음
            - 특정 회사의 모든 채용공고 조회 ; Authentication을 통해 companyId를 가져오지 못해 → 회사의 모든 채용공고를 확인하려는 사용자가 채용공고를 실제 생성한 그 회사인지 알 수 없음
            - 회사의 개별 채용공고 조회 ; Authentication을 통해 companyId를 가져오지 못해 → 회사의 특정 채용공고를 확인하려는 사용자가 채용공고를 실제 생성한 그 회사인지 알 수 없음
                - 404 에러 발생 (o.s.web.servlet.PageNotFound : No mapping for GET /company/1/job-posting/1)
            - 특정 채용공고에 대한 모든 지원서 조회 ; Authentication을 통해 companyId를 가져오지 못해 → 회사의 특정 채용공고에 대한 모든 지원서를 확인하려는 사용자가 채용공고를 실제 생성한 그 회사인지 알 수 없음
            - 특정 채용공고에 대한 개별 지원서 조회 ; Authentication을 통해 companyId를 가져오지 못해
                - 해당 채용공고를 작성하지 않은 회사의 id가 입력되어도 → 채용공고 정보가 조회됨 (어떤 회사라도 다른 회사의 채용공고 정보를 조회할 수 있음)
            - (지원자 생성 은 이상이 없음)
            - 지원자 수정 ;  Authentication을 통해 ApplicantId를 가져오지 못해 → 지원자 정보를 수정하려는 사용자가 해당 지원자인지 알 수 없음
            - 지원자 삭제 ; Authentication을 통해 ApplicantId를 가져오지 못해 → 지원자 정보를 삭제하려는 사용자가 해당 지원자인지 알 수 없음
            - 개별 지원자 조회 ; Authentication을 통해 ApplicantId를 가져오지 못해 → 지원자 정보를 삭제하려는 사용자가 해당 지원자인지 알 수 없음
            - 지원자의 지원서 전체 조회 ; Authentication을 통해 ApplicantId를 가져오지 못해 → 지원서를 조회하려는 사용자가 해당 지원자인지 알 수 없음
            - 지원자의 지원서 개별 조회 ; Authentication을 통해 ApplicantId를 가져오지 못해
                - 해당 지원서를 작성하지 않은 지원자의 id가 입력되어도 → 지원서 정보가 조회됨 (지원자 누구라도 다른 지원자의 지원서 정보를 조회할 수 있음)
 
            </details>
        - (사용자 권한 로드를 정상적으로 구현할 경우, 회사 또는 지원자를 식별할 필요가 있는 Service 클래스의 메서드의 기능을 최대한 빨리 구현하기 위해 → 현재 식별 로직을 작성 후 주석처리 중)       
- Application run과 동시에 ApplicationRunner인터페이스를 구현한 DummyDataLoader 클래스를 통해 회사, 지원자, 채용공고, 지원서의 dummy data를 각각 5, 5, 7, 10개씩 생성
    - 생성 순서 : 회사, 지원자를 각각 5개씩 생성 → 각각의 회사가 1개씩 채용공고(총 5개)를 생성 → 생성된 총 7개의 채용공고에 대해 지원자가 총 10개의 지원서를 작성 (채용공고별로 작성된 지원서 갯수는 모두 다르고, 지원을 받지 못한 채용공고도 존재하도록 설정)
- 회사(Company), 지원자(Applicant), 채용공고(JobPosting), 지원서(Application) - 생성(post), 수정(patch)
    - DTO를 통해 생성, 수정할 내용을 전달받은 후 → 빌더 패턴을 활용하여 전달 받은 내용을 기반으로 완료
- 채용공고 리스트에 검색 기능 구현
    - JobPostingRepository에서 `findBy[필드이름]ContainingIgnoreCase(String searchKeyword, Pageable pageable);`  메서드를 구현.
    - 채용공고 제목, 채용 포지션명, 직무소개 내용 순으로 검색어를 조회하여 검색결과를 노출하고, 검색결과가 없으면 모든 채용공고를 노출시킴.
        - 검색어가 채용공고 제목, 채용 포지션명, 직무소개 내용에서 모두 포함되어 있다고 해도 → 채용공고 제목에 포함된 검색결과만 노출시킴 → 추후 채용공고의 모든 필드에 저장된 값을 대상으로 결과가 출력될 수 있도록 검색 로직을 수정할 필요가 있음
- 개별 채용공고 조회 시, 해당 채용공고를 작성한 회사의 다른 모든 채용공고도 조회 가능함
    - 조회하고자 하는 채용공고가 가장 먼저 노출되며, 나머지 채용공고들은 채용공고id 순으로 노출됨.    
- 프로젝트의 기본 요구 사항 외 추가 구현 사항
    - 회사는 자신이 작성한 모든 채용공고 및 개별 채용공고를 조회할 수 있음
    - 회사는 자신이 올린 채용공고별 모든 지원서 리스트 및 개별 지원서를 조회할 수 있음
    - 지원자들은 자신이 작성한 모든 지원서 리스트 및 개별 지원서 조회할 수 있음
    - 로그아웃 : 현재 로그인한 사용자의 권한을 제대로 로드해올 수 없으므로 형식적인 API만 작성한 상태임

### 5. DB 설계
<details>
    
![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/783a4501-2457-4a1c-9745-8e0823e83750)
</details>

### 6. API 설계
<details>
    
![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/f1153cc0-737f-4cf2-af12-0f531afff151)

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/86885231-32db-428b-abbf-3587915f1249)

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/fa594ccc-f826-4d24-97d7-0c07affeb644)

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/86d25fc0-1b85-42fe-8ffe-656313cfdd84)

![image](https://github.com/upqnu/wanted-pre-onboarding-backend/assets/101033614/209899ce-4303-4867-8489-d109de30079e)
</details>

### [Git commit 메시지 컨벤션]
- `feat` : 새로운 기능 추가
- `fix` : 기능 개선 또는 수정, 버그 수정
- `docs` : 문서 수정
- `style` : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- `refactor` : 코드 리펙토링
- `test` : 테스트 코드, 리펙토링 테스트 코드 추가
- `chore` : 빌드 업무 수정, 패키지 매니저 수정
