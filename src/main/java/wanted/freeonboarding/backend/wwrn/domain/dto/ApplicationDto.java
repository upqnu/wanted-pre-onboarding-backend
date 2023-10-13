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
public class ApplicationDto {

    @NotBlank
    private String applyingTitle;

    @NotBlank
    private String applyingIntroduction;

    private String applyingCareers;

    private String applyingAwardsOthers;
}
