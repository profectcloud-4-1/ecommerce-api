package profect.group1.goormdotcom.user.controller.dto.request;
import profect.group1.goormdotcom.user.domain.enums.UserRole;
import lombok.ToString;
import lombok.Getter;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@ToString
@Getter
@AllArgsConstructor
public class RegisterRequestDto {
    @Schema(description = "이름")
    @NotBlank
    private String name;
    @Schema(description = "이메일")
    @NotBlank
    @Email
    private String email;
    @Schema(description = "비밀번호")
    @NotBlank
    private String password;
    @Schema(description = "역할. MASTER, SELLER, CUSTOMER")
    @NotNull
    private UserRole role;
    @Schema(description = "소속 브랜드 ID")
    private String brandId;
}