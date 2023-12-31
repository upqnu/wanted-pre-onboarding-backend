package wanted.freeonboarding.backend.wwrn.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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

    @JsonManagedReference
    @OneToMany(mappedBy = "applicant")
    private List<Application> myApplication;
}
