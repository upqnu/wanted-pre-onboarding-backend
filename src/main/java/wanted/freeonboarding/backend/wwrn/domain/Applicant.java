package wanted.freeonboarding.backend.wwrn.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Applicant extends Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;

    @Column(nullable = false)
    private String nationality;

    @Column(columnDefinition = "TEXT")
    private String careers;

    @Column(columnDefinition = "TEXT")
    private String awardsAndOthers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @OneToMany(mappedBy = "applicant")
    private List<Application> myApplication;
}
