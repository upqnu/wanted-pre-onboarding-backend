package wanted.freeonboarding.backend.wwrn.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingDto {

    @NotBlank
    private String postingTitle;

    @NotBlank
    private String vacantPosition;

    @NotBlank
    private String jobDescription;

    @NotBlank
    private String language;

    @NotBlank
    private Integer compensation;

    @NotBlank
    private String workingNation;

    @NotBlank
    private String workingCity;
}
