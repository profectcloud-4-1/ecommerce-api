package profect.group1.goormdotcom.user.controller.external.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    @Schema(description = "액세스 토큰 (JWT)")
    @NotBlank
    private String accessToken;
    public static LoginResponseDto of(String token) { return new LoginResponseDto(token); }
}


