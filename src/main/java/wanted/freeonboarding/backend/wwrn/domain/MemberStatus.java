package wanted.freeonboarding.backend.wwrn.domain;

public enum MemberStatus {
    ROLE_APPLICANT("지원자"),
    ROLE_COMPANY("채용기업");

    private final String status;

    MemberStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
