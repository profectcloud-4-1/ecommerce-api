package profect.group1.goormdotcom.user.controller.external.v1.dto.response;

import profect.group1.goormdotcom.user.domain.User;
import java.util.List;
import lombok.Getter;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
public class ListResponseDto {
    public static ListResponseDto of(List<User> list) {
        return new ListResponseDto(list);
    }

    @Schema(description = "사용자 목록")
    private List<User> list;
}