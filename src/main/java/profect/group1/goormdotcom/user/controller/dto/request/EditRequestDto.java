package profect.group1.goormdotcom.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditRequestDto {
    @Schema(description = "이름")
    @NotBlank
    private String name;
    @Schema(description = "이메일")
    @NotBlank
    @Email
    private String email;
}