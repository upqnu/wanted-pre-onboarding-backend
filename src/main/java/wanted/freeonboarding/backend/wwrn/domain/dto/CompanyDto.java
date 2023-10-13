package wanted.freeonboarding.backend.wwrn.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wanted.freeonboarding.backend.wwrn.domain.MemberStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String headOfficeNation;

    @NotBlank
    private String headOfficeCity;

    @NotBlank
    private MemberStatus status;
}
