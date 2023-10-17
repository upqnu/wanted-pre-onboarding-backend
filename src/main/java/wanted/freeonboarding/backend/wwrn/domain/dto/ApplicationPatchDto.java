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
public class ApplicationPatchDto {

    private Long applicantId;

    @NotBlank(message = "지원서 제목은 반드시 입력해야 합니다.")
    private String applyingTitle;

    @NotBlank(message = "자기 소개는 반드시 입력해야 합니다.")
    private String applyingIntroduction;

    private String applyingCareers;

    private String applyingAwardsOthers;
}
