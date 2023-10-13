package wanted.freeonboarding.backend.wwrn.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @Column(nullable = false)
    private String applyingTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String applyingIntroduction;

    @Column(columnDefinition = "TEXT")
    private String applyingCareers;

    @Column(columnDefinition = "TEXT")
    private String applyingAwardsOthers;
}
