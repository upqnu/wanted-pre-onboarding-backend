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
public class JobPostingCreateDto {

    private Long companyId;

    @NotBlank(message = "채용공고 제목은 반드시 입력해야 합니다.")
    private String postingTitle;

    @NotBlank(message = "채용 포지션은 반드시 입력해야 합니다.")
    private String vacantPosition;

    @NotBlank(message = "직무 내용은 반드시 입력해야 합니다.")
    private String jobDescription;

    @NotBlank(message = "사용 기술은 반드시 입력해야 합니다.")
    private String language;

    @NotBlank(message = "채용 시 보상금액은 반드시 입력해야 합니다.")
    private Integer compensation;

    @NotBlank(message = "근무할 국가는 반드시 입력해야 합니다.")
    private String workingNation;

    @NotBlank(message = "근무할 도시는 반드시 입력해야 합니다.")
    private String workingCity;
}
