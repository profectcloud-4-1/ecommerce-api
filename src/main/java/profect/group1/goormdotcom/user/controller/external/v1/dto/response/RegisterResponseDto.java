package profect.group1.goormdotcom.user.controller.external.v1.dto.response;

import lombok.Getter;
import lombok.AllArgsConstructor;
import profect.group1.goormdotcom.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class RegisterResponseDto {

    public static RegisterResponseDto of(User user) {
        return new RegisterResponseDto(user.getId().toString());
    }

    @Schema(description = "가입완료된 사용자 ID")
    @NotBlank
    private String id;
}