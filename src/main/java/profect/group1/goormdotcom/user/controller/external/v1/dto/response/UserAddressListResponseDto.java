package profect.group1.goormdotcom.user.controller.external.v1.dto.response;

import java.util.List;
import profect.group1.goormdotcom.user.domain.UserAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class UserAddressListResponseDto {
    @Schema(description = "배송지 목록")
    private List<UserAddress> list;
}