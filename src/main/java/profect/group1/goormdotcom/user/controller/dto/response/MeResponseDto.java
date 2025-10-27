package profect.group1.goormdotcom.user.controller.dto.response;

import profect.group1.goormdotcom.user.domain.User;
import lombok.Getter;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
public class MeResponseDto {
    public static MeResponseDto of(User user) {
        return new MeResponseDto(user);
    }

    @Schema(description = "내 정보")
    private User me;
}