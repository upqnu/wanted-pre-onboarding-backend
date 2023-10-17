package wanted.freeonboarding.backend.wwrn.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "job_posting")
public class JobPosting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobPostingId;

    @Column(nullable = false)
    private String postingTitle;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String vacantPosition;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jobDescription;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private Integer compensation;

    @Column(nullable = false)
    private String workingNation;

    @Column(nullable = false)
    private String workingCity;
}
